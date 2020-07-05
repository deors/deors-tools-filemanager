package deors.tools.filemanager.filenameinserter;

import java.io.File;

import javax.swing.SwingWorker;

/**
 * The FileNameInserter worker.
 *
 * @author deors
 * @version 1.0
 */
public class FileNameInserterWorker
    extends SwingWorker<Integer, Object> {

    /**
     * The process.
     */
    private FileNameInserterProcess process;

    /**
     * Constructor that initializes the process.
     *
     * @param parameters the process parameters
     */
    public FileNameInserterWorker(FileNameInserterObject parameters) {

        super();

        this.process = new FileNameInserterProcess(
            parameters.getInsertPos().bigDecimalValue().intValue(),
            parameters.getInsertText().stringValue(),
            new File(parameters.getRootDir().stringValue()),
            parameters.getRecurse().booleanValue(),
            parameters.getApplyToFiles().booleanValue(),
            parameters.getApplyToDirectories().booleanValue(),
            parameters.getFilter().booleanValue(),
            parameters.getFilterRegex().stringValue());
    }

    /**
     * Runs the process.
     *
     * @return the process error count
     */
    public Integer doInBackground() {

        // runs the process
        int errors = process.doProcess();

        return errors;
    }

    /**
     * Returns the process instance.
     *
     * @return the process instance
     */
    public FileNameInserterProcess getProcess() {

        return process;
    }
}
