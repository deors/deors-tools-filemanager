package deors.tools.filemanager.putzerointimeinator;

import static deors.core.commons.StringToolkit.replace;

import java.io.File;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deors.core.commons.AbstractFileTool;
import deors.tools.filemanager.Resources;

/**
 * The PutZeroInTimeInator process.
 *
 * @author deors
 * @version 1.0
 */
public class PutZeroInTimeInatorProcess
    extends AbstractFileTool {

    /**
     * Main entry point for the tool when executed from command line.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        final int paramRootDir = 0;

        Logger logger = LoggerFactory.getLogger(PutZeroInTimeInatorProcess.class);

        /* not needed while the tool has no parameters
        if (args.length < paramRootDir) {
            logger.info(Resources.PRO_MISSING_PARAMETERS);
            return;
        }*/

        PutZeroInTimeInatorProcess process = new PutZeroInTimeInatorProcess(
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
     * Process constructor.
     *
     * @param rootDir the root directory
     * @param recurse whether to recurse directories when searching for files to process
     */
    public PutZeroInTimeInatorProcess(File rootDir, boolean recurse) {

        this(rootDir, recurse, true, true, false, null);
    }

    /**
     * Process constructor.
     *
     * @param rootDir the root directory
     * @param recurse whether to recurse directories when searching for files to process
     * @param applyToFiles whether the process will be executed to files
     * @param applyToDirectories whether the process will be executed to directories
     * @param filter whether to filter out files by their name
     * @param filterRegex the regular expression used to filter out files by their name
     */
    public PutZeroInTimeInatorProcess(File rootDir, boolean recurse, boolean applyToFiles,
                                      boolean applyToDirectories, boolean filter, String filterRegex) {

        super(rootDir, recurse, applyToFiles, applyToDirectories, filter, filterRegex);
    }

    /**
     * Executes actions over a file or directory.
     *
     * @param file the file or directory that will be processed
     */
    private void applyActions(File file) {

        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(file.lastModified());
        time.set(Calendar.HOUR_OF_DAY, 0);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        time.set(Calendar.MILLISECOND, 0);

        if (file.setLastModified(time.getTimeInMillis())) {
            logInfo(replace(Resources.LOG_DATE_MODIFIED, file.getAbsolutePath()));
        } else {
            logError(replace(Resources.LOG_ERROR_MODIFYING_DATE, file.getAbsolutePath()));
        }
    }

    /**
     * Executes actions over a directory.
     *
     * @param directory the directory that will be processed
     */
    @Override
    protected void applyActionsToDirectory(File directory) {

        applyActions(directory);
    }

    /**
     * Executes actions over a file.
     *
     * @param file the file that will be processed
     */
    @Override
    protected void applyActionsToFile(File file) {

        applyActions(file);
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
