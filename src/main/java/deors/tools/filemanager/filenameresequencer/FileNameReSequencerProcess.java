package deors.tools.filemanager.filenameresequencer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deors.core.commons.AbstractFileTool;
import deors.core.commons.StringToolkit;
import deors.tools.filemanager.Resources;

/**
 * The FileNameReSequencer process.
 *
 * @author deors
 * @version 1.0
 */
public class FileNameReSequencerProcess
    extends AbstractFileTool {

    /**
     * Suffix used for intermediate names during the two-step rename.
     */
    private static final String TEMP_SUFFIX = ".reseqtmp"; //$NON-NLS-1$

    /**
     * Main entry point for the tool when executed from command line.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        final int paramPrefix = 0;
        final int paramOffset = 1;
        final int paramStart = 2;
        final int paramRootDir = 3;

        Logger logger = LoggerFactory.getLogger(FileNameReSequencerProcess.class);

        if (args.length <= paramRootDir) {
            logger.info(Resources.PRO_MISSING_PARAMETERS);
            return;
        }

        FileNameReSequencerProcess process = new FileNameReSequencerProcess(
            args[paramPrefix],
            Integer.parseInt(args[paramOffset]),
            Integer.parseInt(args[paramStart]),
            new File(args[paramRootDir]), true);

        logger.info(Resources.PRO_STARTED);

        int errors = process.doProcess();

        if (errors == 0) {
            logger.info(Resources.PRO_FINISHED_OK);
        } else {
            logger.info(Resources.PRO_FINISHED_ERROR, Integer.toString(errors));
        }
    }

    /**
     * The prefix used to select already sequenced files.
     */
    private String prefix;

    /**
     * The offset applied to matching sequence numbers.
     */
    private int offset;

    /**
     * The first sequence number included in the resequence.
     * A value of <code>0</code> includes every matching file.
     */
    private int start;

    /**
     * Files collected during the scan, indexed by containing directory.
     */
    private final Map<File, List<File>> fileMap = new LinkedHashMap<File, List<File>>();

    /**
     * Process constructor.
     *
     * @param prefix the prefix
     * @param offset the offset
     * @param start the start index
     * @param rootDir the root directory
     * @param recurse whether to recurse directories when searching for files to process
     */
    public FileNameReSequencerProcess(String prefix, int offset, int start, File rootDir, boolean recurse) {

        this(prefix, offset, start, rootDir, recurse, false, null);
    }

    /**
     * Process constructor.
     *
     * @param prefix the prefix
     * @param offset the offset
     * @param start the start index
     * @param rootDir the root directory
     * @param recurse whether to recurse directories when searching for files to process
     * @param filter whether to filter out files by their name
     * @param filterRegex the regular expression used to filter out files by their name
     */
    public FileNameReSequencerProcess(String prefix, int offset, int start, File rootDir, boolean recurse, boolean filter, String filterRegex) {

        super(rootDir, recurse, true, false, filter, filterRegex);

        this.prefix = prefix == null ? "" : prefix; //$NON-NLS-1$
        this.offset = offset;
        this.start = start;
    }

    /**
     * Executes actions over a directory.
     *
     * @param directory the directory that will be processed
     */
    @Override
    protected void applyActionsToDirectory(File directory) {
    }

    /**
     * Collects a file so the resequence can be simulated after the scan.
     *
     * @param file the file that will be processed
     */
    @Override
    protected void applyActionsToFile(File file) {

        File parent = file.getParentFile();
        List<File> fileList = fileMap.get(parent);
        if (fileList == null) {
            fileList = new ArrayList<File>();
            fileMap.put(parent, fileList);
        }
        fileList.add(file);
    }

    /**
     * Simulates and, if there are no conflicts, applies the resequence in each directory.
     */
    @Override
    protected void doPostProcess() {

        Set<Entry<File, List<File>>> fileSet = fileMap.entrySet();
        for (Entry<File, List<File>> entry : fileSet) {
            resequenceDirectory(entry.getKey(), entry.getValue());
        }
    }

    /**
     * The actions to be applied before the process starts.
     */
    @Override
    protected void doPreProcess() {

        // nothing to do
    }

    /**
     * Simulates the resequence in one directory and applies it only when no conflicts are found.
     *
     * @param directory the directory that will be processed
     * @param files the files collected in that directory
     */
    private void resequenceDirectory(File directory, List<File> files) {

        Map<String, File> filesByName = new HashMap<String, File>();
        Set<Integer> protectedSequences = new HashSet<Integer>();

        File[] listed = directory.listFiles();
        if (listed != null) {
            for (File existing : listed) {
                if (!existing.isFile()) {
                    continue;
                }
                filesByName.put(normalizeName(existing.getName()), existing);
                SequencedName parsed = parseSequencedName(existing.getName());
                if (parsed != null && start > 0 && parsed.sequence() < start) {
                    protectedSequences.add(Integer.valueOf(parsed.sequence()));
                }
            }
        }

        List<PlannedRename> plans = new ArrayList<PlannedRename>();

        for (File file : files) {
            SequencedName parsed = parseSequencedName(file.getName());
            if (parsed == null) {
                continue;
            }
            if (start > 0 && parsed.sequence() < start) {
                continue;
            }

            int newSequence = parsed.sequence() + offset;
            String newName = buildName(parsed, newSequence);
            if (file.getName().equals(newName)) {
                continue;
            }

            plans.add(new PlannedRename(file, newSequence, newName, null));
        }

        if (plans.isEmpty()) {
            return;
        }

        boolean conflicts = simulatePlans(plans, filesByName, protectedSequences);

        if (conflicts) {
            logError(Resources.LOG_RESEQ_SIMULATION_FAILED);
            return;
        }

        logInfo(Resources.LOG_RESEQ_SIMULATION_OK, Integer.toString(plans.size()));
        applyPlans(directory, plans);
    }

    /**
     * Checks every planned rename for negative results, collisions with files below
     * the start index, duplicate targets and existing names that are not being moved.
     *
     * @param plans the planned renames
     * @param filesByName existing files in the directory indexed by normalized name
     * @param protectedSequences sequence numbers belonging to files below the start index
     *
     * @return whether at least one conflict was found
     */
    private boolean simulatePlans(List<PlannedRename> plans, Map<String, File> filesByName,
                                  Set<Integer> protectedSequences) {

        boolean conflicts = false;
        Set<String> movingNames = new HashSet<String>();
        Map<String, String> targetOwners = new LinkedHashMap<String, String>();

        for (PlannedRename plan : plans) {
            movingNames.add(normalizeName(plan.source().getName()));
        }

        for (PlannedRename plan : plans) {
            logInfo(Resources.LOG_RESEQ_PLAN, new String[] {plan.source().getName(), plan.newName()});

            if (plan.newSequence() < 0) {
                logError(Resources.LOG_RESEQ_NEGATIVE,
                    new String[] {plan.source().getName(), Integer.toString(plan.newSequence())});
                conflicts = true;
                continue;
            }

            if (protectedSequences.contains(Integer.valueOf(plan.newSequence()))) {
                logError(Resources.LOG_RESEQ_CONFLICT_BELOW_START,
                    new String[] {plan.source().getName(), plan.newName(), Integer.toString(plan.newSequence())});
                conflicts = true;
                continue;
            }

            String targetKey = normalizeName(plan.newName());
            String previous = targetOwners.put(targetKey, plan.source().getName());
            if (previous != null) {
                logError(Resources.LOG_RESEQ_CONFLICT_DUPLICATE,
                    new String[] {previous, plan.source().getName(), plan.newName()});
                conflicts = true;
                continue;
            }

            if (!movingNames.contains(targetKey)) {
                File occupant = filesByName.get(targetKey);
                if (occupant == null) {
                    File targetFile = new File(plan.source().getParentFile(), plan.newName());
                    if (targetFile.exists()) {
                        occupant = targetFile;
                    }
                }
                if (occupant != null) {
                    logError(Resources.LOG_RESEQ_CONFLICT_EXISTING,
                        new String[] {plan.source().getName(), plan.newName()});
                    conflicts = true;
                }
            }
        }

        return conflicts;
    }

    /**
     * Applies the planned renames in two steps using intermediate names.
     *
     * @param directory the directory that will be processed
     * @param plans the planned renames
     */
    private void applyPlans(File directory, List<PlannedRename> plans) {

        List<PlannedRename> relocated = new ArrayList<PlannedRename>();
        for (PlannedRename plan : plans) {
            File temp = uniqueTempFile(plan.source());
            if (!plan.source().renameTo(temp)) {
                logError(Resources.LOG_RESEQ_TEMP_FAILED,
                    new String[] {plan.source().getName(), temp.getName()});
                rollbackTemporaryNames(relocated);
                return;
            }
            relocated.add(plan.withTemp(temp));
        }

        for (PlannedRename plan : relocated) {
            File dest = new File(directory, plan.newName());
            if (!plan.temp().renameTo(dest)) {
                logError(Resources.LOG_ERROR_RENAMING_FILE,
                    new String[] {plan.source().getName(), plan.newName()});
            } else {
                logInfo(Resources.LOG_FILE_RENAMED,
                    new String[] {plan.source().getName(), plan.newName()});
            }
        }
    }

    /**
     * Restores files already moved to a temporary name back to their original name.
     *
     * @param plans the planned renames
     */
    private void rollbackTemporaryNames(List<PlannedRename> plans) {

        for (PlannedRename plan : plans) {
            if (plan.temp() != null && plan.temp().exists()) {
                if (!plan.temp().renameTo(plan.source())) {
                    logError(Resources.LOG_ERROR_RENAMING_FILE,
                        new String[] {plan.temp().getName(), plan.source().getName()});
                }
            }
        }
    }

    /**
     * Returns a temporary file path that does not exist yet in the same directory.
     *
     * @param source the file that will be renamed
     *
     * @return a unique temporary file
     */
    private File uniqueTempFile(File source) {

        File parent = source.getParentFile();
        String base = source.getName() + TEMP_SUFFIX;
        File temp = new File(parent, base);
        int n = 0;
        while (temp.exists()) {
            n++;
            temp = new File(parent, base + n);
        }
        return temp;
    }

    /**
     * Parses a file name into prefix, sequence token and remaining suffix.
     *
     * @param name the file name
     *
     * @return the parsed sequence data, or <code>null</code> if the name does not match
     */
    SequencedName parseSequencedName(String name) {

        if (name == null || !name.startsWith(prefix)) {
            return null;
        }

        int i = prefix.length();
        if (i >= name.length() || !Character.isDigit(name.charAt(i))) {
            return null;
        }

        int digitsStart = i;
        while (i < name.length() && Character.isDigit(name.charAt(i))) {
            i++;
        }

        String numberToken = name.substring(digitsStart, i);
        int sequence;
        try {
            sequence = Integer.parseInt(numberToken);
        } catch (NumberFormatException nfe) {
            return null;
        }

        return new SequencedName(numberToken, sequence, name.substring(i));
    }

    /**
     * Builds the new file name keeping the original numeric padding.
     *
     * @param parsed the parsed original name
     * @param newSequence the new sequence number
     *
     * @return the new file name
     */
    String buildName(SequencedName parsed, int newSequence) {

        String newToken = Integer.toString(newSequence);
        if (newSequence >= 0 && newToken.length() < parsed.numberToken().length()) {
            newToken = StringToolkit.padLeft(newToken, parsed.numberToken().length(), '0');
        }
        return prefix + newToken + parsed.suffix();
    }

    /**
     * Returns a name key suitable for case-insensitive file systems.
     *
     * @param name the file name
     *
     * @return the normalized name
     */
    private String normalizeName(String name) {

        return name.toLowerCase(Locale.ROOT);
    }

    /**
     * Parsed sequence information extracted from a file name.
     *
     * @param numberToken the original numeric token, including padding
     * @param sequence the numeric sequence value
     * @param suffix the remainder of the file name after the sequence token
     */
    record SequencedName(String numberToken, int sequence, String suffix) {
    }

    /**
     * A planned rename calculated during the simulation.
     *
     * @param source the original file
     * @param newSequence the resulting sequence number
     * @param newName the destination file name
     * @param temp the intermediate file used during the two-step rename
     */
    private record PlannedRename(File source, int newSequence, String newName, File temp) {

        /**
         * Returns a copy of this plan with the given intermediate file.
         *
         * @param temp the intermediate file
         *
         * @return a new plan with the intermediate file set
         */
        private PlannedRename withTemp(File temp) {

            return new PlannedRename(source, newSequence, newName, temp);
        }
    }
}
