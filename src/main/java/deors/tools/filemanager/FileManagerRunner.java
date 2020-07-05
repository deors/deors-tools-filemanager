package deors.tools.filemanager;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import deors.core.sensible.SensibleToolkit;

/**
 * Runnable class used to run the application.
 *
 * @author deors
 * @version 1.0
 */
public final class FileManagerRunner implements Runnable {

    /**
     * The root directory received as main parameter.
     */
    public static String rootDir;

    /**
     * The GUI look and feel - Substance Creme Coffee.
     */
    private static final String LOOK_AND_FEEL = "org.pushingpixels.substance.api.skin.SubstanceCremeCoffeeLookAndFeel"; //$NON-NLS-1$

    /**
     * Default constructor.
     */
    public FileManagerRunner() {
        super();
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(String[] args) {

        if (args.length != 0) {
            rootDir = args[0];
        }
        SwingUtilities.invokeLater(new FileManagerRunner());
    }

    /**
     * Runs the application by creating, locating and showing
     * its main window.
     */
    public void run() {

        if (!SensibleToolkit.setLookAndFeel(LOOK_AND_FEEL)) {
            SensibleToolkit.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }

        FileManagerFrame application = new FileManagerFrame();
        SensibleToolkit.centerWindow(application);
        application.setVisible(true);
    }
}
