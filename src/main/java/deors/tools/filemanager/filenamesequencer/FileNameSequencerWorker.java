package deors.tools.filemanager.filenamesequencer;

import java.io.File;

import javax.swing.SwingWorker;

/**
 * The FileNameSequencer worker.
 *
 * @author deors
 * @version 1.0
 */
public class FileNameSequencerWorker
    extends SwingWorker<Integer, Object> {

    /**
     * The process.
     */
    private FileNameSequencerProcess process;

    /**
     * Constructor that initializes the process.
     *
     * @param parameters the process parameters
     */
    public FileNameSequencerWorker(FileNameSequencerObject parameters) {

        super();

        this.process = new FileNameSequencerProcess(
            parameters.getPrefix().stringValue(),
            parameters.getOffset().bigDecimalValue().intValue(),
            parameters.getPadding().bigDecimalValue().intValue(),
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
    public FileNameSequencerProcess getProcess() {

        return process;
    }
}
