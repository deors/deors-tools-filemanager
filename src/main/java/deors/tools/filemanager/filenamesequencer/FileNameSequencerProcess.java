package deors.tools.filemanager.filenamesequencer;

import static deors.core.commons.StringToolkit.replaceMultiple;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deors.core.commons.AbstractFileTool;
import deors.core.commons.StringToolkit;
import deors.core.commons.io.IOToolkit;
import deors.tools.filemanager.Resources;

/**
 * The FileNameSequencer process.
 *
 * @author deors
 * @version 1.0
 */
public class FileNameSequencerProcess
    extends AbstractFileTool {

    /**
     * Main entry point for the tool when executed from command line.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        final int paramPrefix = 0;
        final int paramOffset = 1;
        final int paramPadding = 2;
        final int paramRootDir = 3;

        Logger logger = LoggerFactory.getLogger(FileNameSequencerProcess.class);

        if (args.length < paramRootDir) {
            logger.info(Resources.PRO_MISSING_PARAMETERS);
            return;
        }

        FileNameSequencerProcess process = new FileNameSequencerProcess(
            args[paramPrefix],
            Integer.parseInt(args[paramOffset]),
            Integer.parseInt(args[paramPadding]),
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
     * The prefix.
     */
    private String prefix;

    /**
     * The offset.
     */
    private int offset;

    /**
     * The padding.
     */
    private int padding;

    /**
     * The file count.
     */
    private int count;

    /**
     * Process constructor.
     *
     * @param prefix the prefix
     * @param offset the offset
     * @param padding the padding
     * @param rootDir the root directory
     * @param recurse whether to recurse directories when searching for files to process
     */
    public FileNameSequencerProcess(String prefix, int offset, int padding, File rootDir, boolean recurse) {

        this(prefix, offset, padding, rootDir, recurse, false, null);
    }

    /**
     * Process constructor.
     *
     * @param prefix the prefix
     * @param offset the offset
     * @param padding the padding
     * @param rootDir the root directory
     * @param recurse whether to recurse directories when searching for files to process
     * @param filter whether to filter out files by their name
     * @param filterRegex the regular expression used to filter out files by their name
     */
    public FileNameSequencerProcess(String prefix, int offset, int padding, File rootDir, boolean recurse, boolean filter, String filterRegex) {

        super(rootDir, recurse, true, false, filter, filterRegex);

        this.prefix = prefix == null ? "" : prefix;
        this.offset = offset;
        this.padding = padding;
        this.count = 1;
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
     * Executes actions over a file.
     *
     * @param file the file that will be processed
     */
    @Override
    protected void applyActionsToFile(File file) {

        final String dot = "."; //$NON-NLS-1$

        String newName = prefix;
        String countString = Integer.toString(offset + count++);

        if (padding > 0) {
            newName = prefix.concat(StringToolkit.padLeft(countString, padding, '0'));
        } else {
            newName = prefix.concat(countString);
        }

        newName = newName.concat(dot).concat(IOToolkit.extractExtension(file));

        File newFile = new File(file.getParentFile(), newName);

        if (file.getName().equals(newFile.getName())) {
            return;
        }

        if (file.renameTo(newFile)) {
            logInfo(replaceMultiple(Resources.LOG_FILE_RENAMED, new String[] {file.getName(), newFile.getName()}));
        } else {
            logError(replaceMultiple(Resources.LOG_ERROR_RENAMING_FILE, new String[] {file.getName(), newFile.getName()}));
        }
    }

    /**
     * The actions to be applied after the process ends.
     */
    @Override
    protected void doPostProcess() {

        // nothing to do
    }

    /**
     * The actions to be applied before the process starts.
     */
    @Override
    protected void doPreProcess() {

        // nothing to do
    }
}
