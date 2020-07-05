package deors.tools.filemanager.datechanger;

import java.io.File;

import javax.swing.SwingWorker;

/**
 * The DateChanger worker.
 *
 * @author deors
 * @version 1.0
 */
public class DateChangerWorker
    extends SwingWorker<Integer, Object> {

    /**
     * The process.
     */
    private DateChangerProcess process;

    /**
     * Constructor that initializes the process.
     *
     * @param parameters the process parameters
     */
    public DateChangerWorker(DateChangerObject parameters) {

        super();

        this.process = new DateChangerProcess(
            parameters.getNewDate().dateValue(),
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
    public DateChangerProcess getProcess() {

        return process;
    }
}
