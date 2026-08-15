package deors.tools.filemanager.filenameresequencer;

import java.io.File;

import javax.swing.SwingWorker;

/**
 * The FileNameReSequencer worker.
 *
 * @author deors
 * @version 1.0
 */
public class FileNameReSequencerWorker
    extends SwingWorker<Integer, Object> {

    /**
     * The process.
     */
    private FileNameReSequencerProcess process;

    /**
     * Constructor that initializes the process.
     *
     * @param parameters the process parameters
     */
    public FileNameReSequencerWorker(FileNameReSequencerObject parameters) {

        super();

        this.process = new FileNameReSequencerProcess(
            parameters.getPrefix().stringValue(),
            parameters.getOffset().bigDecimalValue().intValue(),
            parameters.getStart().bigDecimalValue().intValue(),
            new File(parameters.getRootDir().stringValue()),
            parameters.getRecurse().booleanValue(),
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
    public FileNameReSequencerProcess getProcess() {

        return process;
    }
}
