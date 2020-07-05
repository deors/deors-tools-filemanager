package deors.tools.filemanager.lowercaserenamer;

import java.io.File;

import javax.swing.SwingWorker;

/**
 * The LowerCaseRenamer worker.
 *
 * @author deors
 * @version 1.0
 */
public class LowerCaseRenamerWorker
    extends SwingWorker<Integer, Object> {

    /**
     * The process.
     */
    private LowerCaseRenamerProcess process;

    /**
     * Constructor that initializes the process.
     *
     * @param parameters the process parameters
     */
    public LowerCaseRenamerWorker(LowerCaseRenamerObject parameters) {

        super();

        this.process = new LowerCaseRenamerProcess(
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
    public LowerCaseRenamerProcess getProcess() {

        return process;
    }
}
