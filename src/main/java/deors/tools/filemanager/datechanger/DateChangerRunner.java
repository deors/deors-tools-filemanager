package deors.tools.filemanager.datechanger;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import deors.core.sensible.SensibleToolkit;

/**
 * Runnable class used to run the application.
 *
 * @author deors
 * @version 1.0
 */
public final class DateChangerRunner implements Runnable {

    /**
     * The GUI look and feel - Substance Creme Coffee.
     */
    private static final String LOOK_AND_FEEL = "org.pushingpixels.substance.api.skin.SubstanceCremeCoffeeLookAndFeel"; //$NON-NLS-1$

    /**
     * Default constructor.
     */
    public DateChangerRunner() {
        super();
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new DateChangerRunner());
    }

    /**
     * Runs the application by creating, locating and showing
     * its main window.
     */
    public void run() {

        if (!SensibleToolkit.setLookAndFeel(LOOK_AND_FEEL)) {
            SensibleToolkit.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }

        DateChangerFrame application = new DateChangerFrame();
        SensibleToolkit.centerWindow(application);
        application.setVisible(true);
    }
}
