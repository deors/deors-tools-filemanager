package deors.tools.filemanager.filenameinserter;

import static deors.core.commons.StringToolkit.replaceMultiple;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deors.core.commons.AbstractFileTool;
import deors.tools.filemanager.Resources;

/**
 * The FileNameInserter process.
 *
 * @author deors
 * @version 1.0
 */
public class FileNameInserterProcess
    extends AbstractFileTool {

    /**
     * Main entry point for the tool when executed from command line.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        final int paramInsertPos = 0;
        final int paramInsertText = 1;
        final int paramRootDir = 2;

        Logger logger = LoggerFactory.getLogger(FileNameInserterProcess.class);

        if (args.length < paramRootDir) {
            logger.info(Resources.PRO_MISSING_PARAMETERS);
            return;
        }

        FileNameInserterProcess process = new FileNameInserterProcess(
            Integer.parseInt(args[paramInsertPos]),
            args[paramInsertText],
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
     * The insertion point.
     */
    private int insertPos;

    /**
     * The text to insert.
     */
    private String insertText;

    /**
     * Process constructor.
     *
     * @param insertPos the insertion point
     * @param insertText the text to insert
     * @param rootDir the root directory
     * @param recurse whether to recurse directories when searching for files to process
     */
    public FileNameInserterProcess(int insertPos, String insertText, File rootDir, boolean recurse) {

        this(insertPos, insertText, rootDir, recurse, true, true, false, null);
    }

    /**
     * Process constructor.
     *
     * @param insertPos the insertion point
     * @param insertText the text to insert
     * @param rootDir the root directory
     * @param recurse whether to recurse directories when searching for files to process
     * @param applyToFiles whether the process will be executed to files
     * @param applyToDirectories whether the process will be executed to directories
     * @param filter whether to filter out files by their name
     * @param filterRegex the regular expression used to filter out files by their name
     */
    public FileNameInserterProcess(int insertPos, String insertText, File rootDir, boolean recurse,
                                   boolean applyToFiles, boolean applyToDirectories, boolean filter,
                                   String filterRegex) {

        super(rootDir, recurse, applyToFiles, applyToDirectories, filter, filterRegex);
        this.insertPos = insertPos;
        this.insertText = insertText;
    }

    /**
     * Executes actions over a file or directory.
     *
     * @param file the file or directory that will be processed
     */
    private void applyActions(File file) {

        StringBuilder newName = new StringBuilder();
        newName.append(file.getName().substring(0, insertPos));
        newName.append(insertText);
        newName.append(file.getName().substring(insertPos));

        File newFile = new File(
            file.getParentFile(),
            newName.toString());

        if (file.getName().equals(newFile.getName())) {
            return;
        }

        if (file.renameTo(newFile)) {
            logInfo(replaceMultiple(Resources.LOG_FILE_RENAMED,
                new String[] {file.getName(), newFile.getName()}));
        } else {
            logError(replaceMultiple(Resources.LOG_ERROR_RENAMING_FILE,
                new String[] {file.getName(), newFile.getName()}));
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
