package deors.tools.filemanager.filerenamer;

import java.io.File;

import javax.swing.SwingWorker;

/**
 * The FileRenamer worker.
 *
 * @author deors
 * @version 1.0
 */
public class FileRenamerWorker
    extends SwingWorker<Integer, Object> {

    /**
     * The process.
     */
    private FileRenamerProcess process;

    /**
     * Constructor that initializes the process.
     *
     * @param parameters the process parameters
     */
    public FileRenamerWorker(FileRenamerObject parameters) {

        super();

        this.process = new FileRenamerProcess(
            parameters.getRegex().stringValue(),
            parameters.getReplacement().stringValue(),
            new File(parameters.getRootDir().stringValue()),
            parameters.getRecurse().booleanValue());
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
    public FileRenamerProcess getProcess() {

        return process;
    }
}
