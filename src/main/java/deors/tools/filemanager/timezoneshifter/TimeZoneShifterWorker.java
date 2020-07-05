package deors.tools.filemanager.timezoneshifter;

import java.io.File;

import javax.swing.SwingWorker;

/**
 * The TimeZoneShifter worker.
 *
 * @author deors
 * @version 1.0
 */
public class TimeZoneShifterWorker
extends SwingWorker<Integer, Object> {

    /**
     * The process.
     */
    private TimeZoneShifterProcess process;

    /**
     * Constructor that initializes the process.
     *
     * @param parameters the process parameters
     */
    public TimeZoneShifterWorker(TimeZoneShifterObject parameters) {

        super();

        this.process = new TimeZoneShifterProcess(
            parameters.getTzShift().bigDecimalValue().intValue(),
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
    public TimeZoneShifterProcess getProcess() {

        return process;
    }
}
