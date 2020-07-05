package deors.tools.filemanager.datechanger;

import static deors.core.commons.StringToolkit.replace;

import java.io.File;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deors.core.commons.AbstractFileTool;
import deors.core.sensible.SensibleDateTime;
import deors.tools.filemanager.Resources;

/**
 * The DateChanger process.
 *
 * @author deors
 * @version 1.0
 */
public class DateChangerProcess
    extends AbstractFileTool {

    /**
     * Main entry point for the tool when executed from command line.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        final int paramNewDate = 0;
        final int paramRootDir = 1;

        Logger logger = LoggerFactory.getLogger(DateChangerProcess.class);

        if (args.length < paramRootDir) {
            logger.info(Resources.PRO_MISSING_PARAMETERS);
            return;
        }

        DateChangerProcess process = new DateChangerProcess(
            new SensibleDateTime(args[paramNewDate]).dateValue(),
            new File(args[paramRootDir]), true, true, true, false, null);

        logger.info(Resources.PRO_STARTED);

        int errors = process.doProcess();

        if (errors == 0) {
            logger.info(Resources.PRO_FINISHED_OK);
        } else {
            logger.info(Resources.PRO_FINISHED_ERROR, Integer.toString(errors));
        }
    }

    /**
     * The new date.
     */
    private Date newDate;

    /**
     * Process constructor.
     *
     * @param newDate the new date
     * @param rootDir the root directory
     * @param recurse whether to recurse directories when searching for files to process
     */
    public DateChangerProcess(Date newDate, File rootDir, boolean recurse) {

        this(newDate, rootDir, recurse, true, true, false, null);
    }

    /**
     * Process constructor.
     *
     * @param newDate the new date
     * @param rootDir the root directory
     * @param recurse whether to recurse directories when searching for files to process
     * @param applyToFiles whether the process will be executed to files
     * @param applyToDirectories whether the process will be executed to directories
     * @param filter whether to filter out files by their name
     * @param filterRegex the regular expression used to filter out files by their name
     */
    public DateChangerProcess(Date newDate, File rootDir, boolean recurse, boolean applyToFiles,
                              boolean applyToDirectories, boolean filter, String filterRegex) {

        super(rootDir, recurse, applyToFiles, applyToDirectories, filter, filterRegex);
        this.newDate = newDate;
    }

    /**
     * Executes actions over a file or directory.
     *
     * @param file the file or directory that will be processed
     */
    private void applyActions(File file) {

        if (file.setLastModified(newDate.getTime())) {
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
