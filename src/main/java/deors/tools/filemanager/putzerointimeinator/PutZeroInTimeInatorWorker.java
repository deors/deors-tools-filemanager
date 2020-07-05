package deors.tools.filemanager.putzerointimeinator;

import java.io.File;

import javax.swing.SwingWorker;

/**
 * The PutZeroInTimeInator worker.
 *
 * @author deors
 * @version 1.0
 */
public class PutZeroInTimeInatorWorker
    extends SwingWorker<Integer, Object> {

    /**
     * The process.
     */
    private PutZeroInTimeInatorProcess process;

    /**
     * Constructor that initializes the process.
     *
     * @param parameters the process parameters
     */
    public PutZeroInTimeInatorWorker(PutZeroInTimeInatorObject parameters) {

        super();

        this.process = new PutZeroInTimeInatorProcess(
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
    public PutZeroInTimeInatorProcess getProcess() {

        return process;
    }
}
