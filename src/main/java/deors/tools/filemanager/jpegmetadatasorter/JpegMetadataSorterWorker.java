package deors.tools.filemanager.jpegmetadatasorter;

import java.io.File;

import javax.swing.SwingWorker;

/**
 * The JPEGSorterAndRenamer worker.
 *
 * @author deors
 * @version 1.0
 */
public class JpegMetadataSorterWorker
    extends SwingWorker<Integer, Object> {

    /**
     * The process.
     */
    private JpegMetadataSorterProcess process;

    /**
     * Constructor that initializes the process.
     *
     * @param parameters the process parameters
     */
    public JpegMetadataSorterWorker(JpegMetadataSorterObject parameters) {

        super();

        this.process = new JpegMetadataSorterProcess(
            new File(parameters.getRootDir().stringValue()),
            parameters.getName().stringValue(),
            parameters.getOffset().bigDecimalValue().intValue(),
            parameters.getSort().booleanValue(),
            parameters.getRename().booleanValue(),
            parameters.getUpdate().booleanValue(),
            parameters.getTest().booleanValue(),
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
    public JpegMetadataSorterProcess getProcess() {

        return process;
    }
}
