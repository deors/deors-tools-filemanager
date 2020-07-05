package deors.tools.filemanager.dateshifter;

import java.io.File;

import javax.swing.SwingWorker;

/**
 * The DateShifter worker.
 *
 * @author deors
 * @version 1.0
 */
public class DateShifterWorker
    extends SwingWorker<Integer, Object> {

    /**
     * The process.
     */
    private DateShifterProcess process;

    /**
     * Constructor that initializes the process.
     *
     * @param parameters the process parameters
     */
    public DateShifterWorker(DateShifterObject parameters) {

        super();

        this.process = new DateShifterProcess(
            parameters.getShift().getTime(),
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
    public DateShifterProcess getProcess() {

        return process;
    }
}
