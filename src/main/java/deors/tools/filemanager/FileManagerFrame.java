package deors.tools.filemanager;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import deors.core.sensible.SensibleToolkit;
import deors.tools.filemanager.datechanger.DateChangerRunner;
import deors.tools.filemanager.dateshifter.DateShifterRunner;
import deors.tools.filemanager.filenameinserter.FileNameInserterRunner;
import deors.tools.filemanager.filenamesequencer.FileNameSequencerRunner;
import deors.tools.filemanager.filerenamer.FileRenamerRunner;
import deors.tools.filemanager.jpegmetadatasorter.JpegMetadataSorterRunner;
import deors.tools.filemanager.lowercaserenamer.LowerCaseRenamerRunner;
import deors.tools.filemanager.putzerointimeinator.PutZeroInTimeInatorRunner;
import deors.tools.filemanager.timezoneshifter.TimeZoneShifterRunner;

/**
 * File Manager main frame.
 *
 * @author deors
 * @version 1.0
 */
public class FileManagerFrame
extends JFrame
implements ActionListener, WindowListener {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = 8099970498912569714L;

    /**
     * The main menu bar.
     */
    private JMenuBar mainMenuBar;

    /**
     * The 'file' menu.
     */
    private JMenu fileMenu;

    /**
     * The 'exit' menu item.
     */
    private JMenuItem exitMenuItem;

    /**
     * The 'help' menu.
     */
    private JMenu helpMenu;

    /**
     * The 'about' menu item.
     */
    private JMenuItem aboutMenuItem;

    /**
     * The main content panel.
     */
    private JPanel mainContentPanel;

    /**
     * The date changer tool button.
     */
    private JButton dateChangerButton;

    /**
     * The date shifter tool button.
     */
    private JButton dateShifterButton;

    /**
     * The time zone shifter tool button.
     */
    private JButton timeZoneShifterButton;

    /**
     * The lower case renamer tool button.
     */
    private JButton lowerCaseRenamerButton;

    /**
     * The file renamer tool button.
     */
    private JButton fileRenamerButton;

    /**
     * The file name inserter tool button.
     */
    private JButton fileNameInserterButton;

    /**
     * The file name sequencer tool button.
     */
    private JButton fileNameSequencerButton;

    /**
     * The putZeroInTimeInator tool button.
     */
    private JButton putZeroInTimeInatorButton;

    /**
     * The jpeg metadata sorter and renamer tool button.
     */
    private JButton jpegMetadataSorterButton;

    /**
     * Default constructor.
     */
    public FileManagerFrame() {

        super();
        initialize();
    }

    /**
     * This method initializes the dialog.
     */
    private void initialize() {

        final int w = 450;
        final int h = 340;

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setTitle(Resources.SUITE_TITLE);
        setSize(w, h);
        setResizable(false);

        setJMenuBar(getMainMenuBar());
        setContentPane(getMainContentPanel());

        addWindowListener(this);

        List<Image> iconList = new ArrayList<Image>();
        iconList.add(SensibleToolkit.createImageIcon(Resources.IMG_DEORS).getImage());
        setIconImages(iconList);
    }

    /**
     * This method initializes the main menu bar.
     *
     * @return the main menu bar
     */
    private JMenuBar getMainMenuBar() {

        if (mainMenuBar == null) {

            mainMenuBar = new JMenuBar();
            mainMenuBar.add(getFileMenu());
            mainMenuBar.add(getHelpMenu());
        }
        return mainMenuBar;
    }

    /**
     * This method initializes the 'file' menu.
     *
     * @return the 'file' menu
     */
    private JMenu getFileMenu() {

        if (fileMenu == null) {
            fileMenu = new JMenu();
            fileMenu.setText(Resources.FILE);
            fileMenu.add(getExitMenuItem());
        }
        return fileMenu;
    }

    /**
     * This method initializes the 'exit' menu item.
     *
     * @return the 'exit' menu item
     */
    private JMenuItem getExitMenuItem() {

        if (exitMenuItem == null) {
            exitMenuItem = new JMenuItem();
            exitMenuItem.setText(Resources.EXIT);
            exitMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK, true));
            exitMenuItem.setIcon(SensibleToolkit.createImageIcon(Resources.IMG_EXIT));
            exitMenuItem.addActionListener(this);
        }
        return exitMenuItem;
    }

    /**
     * Method invoked when the exit menu item is pressed.
     */
    private void exitAction() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        EventQueue eventQueue = toolkit.getSystemEventQueue();
        eventQueue.postEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * This method initializes the 'help' menu.
     *
     * @return the 'help' menu
     */
    private JMenu getHelpMenu() {

        if (helpMenu == null) {
            helpMenu = new JMenu();
            helpMenu.setText(Resources.HELP);
            helpMenu.add(getAboutMenuItem());
        }
        return helpMenu;
    }

    /**
     * This method initializes the 'about' menu item.
     *
     * @return the 'about' menu item
     */
    private JMenuItem getAboutMenuItem() {

        if (aboutMenuItem == null) {
            aboutMenuItem = new JMenuItem();
            aboutMenuItem.setText(Resources.SUITE_ABOUT);
            aboutMenuItem.setIcon(SensibleToolkit.createImageIcon(Resources.IMG_HELP));
            aboutMenuItem.addActionListener(this);
        }
        return aboutMenuItem;
    }

    /**
     * Method invoked when the about menu item is pressed.
     */
    private void aboutAction() {

        JOptionPane.showMessageDialog(
            this,
            Resources.SUITE_ABOUT_TEXT,
            Resources.SUITE_TITLE,
            JOptionPane.INFORMATION_MESSAGE,
            SensibleToolkit.createImageIcon(Resources.IMG_DEORS));
    }

    /**
     * This method initializes the main content panel.
     *
     * @return the main content panel
     */
    private JPanel getMainContentPanel() {

        if (mainContentPanel == null) {

            mainContentPanel = new JPanel();
            mainContentPanel.setLayout(null);

            mainContentPanel.add(getDateChangerButton(), null);
            mainContentPanel.add(getDateShifterButton(), null);
            mainContentPanel.add(getTimeZoneShifterButton(), null);
            mainContentPanel.add(getLowerCaseRenamerButton(), null);
            mainContentPanel.add(getFileRenamerButton(), null);
            mainContentPanel.add(getFileNameInserterButton(), null);
            mainContentPanel.add(getFileNameSequencerButton(), null);
            mainContentPanel.add(getPutZeroInTimeInatorButton(), null);
            mainContentPanel.add(getJpegMetadataSorterButton(), null);
        }
        return mainContentPanel;
    }

    /**
     * This method initializes the date changer tool button.
     *
     *  @return the button
     */
    private JButton getDateChangerButton() {

        final int x = 15;
        final int y = 15;
        final int w = 415;
        final int h = 22;

        if (dateChangerButton == null) {
            dateChangerButton = new JButton();
            dateChangerButton.setBounds(x, y, w, h);
            dateChangerButton.setText(Resources.RUN_DATE_CHANGER);
            dateChangerButton.addActionListener(this);
        }
        return dateChangerButton;
    }

    /**
     * This method initializes the date shifter tool button.
     *
     *  @return the button
     */
    private JButton getDateShifterButton() {

        final int x = 15;
        final int y = 45;
        final int w = 415;
        final int h = 22;

        if (dateShifterButton == null) {
            dateShifterButton = new JButton();
            dateShifterButton.setBounds(x, y, w, h);
            dateShifterButton.setText(Resources.RUN_DATE_SHIFTER);
            dateShifterButton.addActionListener(this);
        }
        return dateShifterButton;
    }

    /**
     * This method initializes the time zone shifter tool button.
     *
     *  @return the button
     */
    private JButton getTimeZoneShifterButton() {

        final int x = 15;
        final int y = 75;
        final int w = 415;
        final int h = 22;

        if (timeZoneShifterButton == null) {
            timeZoneShifterButton = new JButton();
            timeZoneShifterButton.setBounds(x, y, w, h);
            timeZoneShifterButton.setText(Resources.RUN_TIME_ZONE_SHIFTER);
            timeZoneShifterButton.addActionListener(this);
        }
        return timeZoneShifterButton;
    }

    /**
     * This method initializes the putZeroInTimeInator tool button.
     *
     *  @return the button
     */
    private JButton getPutZeroInTimeInatorButton() {

        final int x = 15;
        final int y = 105;
        final int w = 415;
        final int h = 22;

        if (putZeroInTimeInatorButton == null) {
            putZeroInTimeInatorButton = new JButton();
            putZeroInTimeInatorButton.setBounds(x, y, w, h);
            putZeroInTimeInatorButton.setText(Resources.RUN_PUT_ZERO_IN_TIME_INATOR);
            putZeroInTimeInatorButton.addActionListener(this);
        }
        return putZeroInTimeInatorButton;
    }

    /**
     * This method initializes the lower case renamer tool button.
     *
     *  @return the button
     */
    private JButton getLowerCaseRenamerButton() {

        final int x = 15;
        final int y = 135;
        final int w = 415;
        final int h = 22;

        if (lowerCaseRenamerButton == null) {
            lowerCaseRenamerButton = new JButton();
            lowerCaseRenamerButton.setBounds(x, y, w, h);
            lowerCaseRenamerButton.setText(Resources.RUN_LOWER_CASE_RENAMER);
            lowerCaseRenamerButton.addActionListener(this);
        }
        return lowerCaseRenamerButton;
    }

    /**
     * This method initializes the file renamer tool button.
     *
     *  @return the button
     */
    private JButton getFileRenamerButton() {

        final int x = 15;
        final int y = 165;
        final int w = 415;
        final int h = 22;

        if (fileRenamerButton == null) {
            fileRenamerButton = new JButton();
            fileRenamerButton.setBounds(x, y, w, h);
            fileRenamerButton.setText(Resources.RUN_FILE_RENAMER);
            fileRenamerButton.addActionListener(this);
        }
        return fileRenamerButton;
    }

    /**
     * This method initializes the file name inserter tool button.
     *
     *  @return the button
     */
    private JButton getFileNameInserterButton() {

        final int x = 15;
        final int y = 195;
        final int w = 415;
        final int h = 22;

        if (fileNameInserterButton == null) {
            fileNameInserterButton = new JButton();
            fileNameInserterButton.setBounds(x, y, w, h);
            fileNameInserterButton.setText(Resources.RUN_FILE_NAME_INSERTER);
            fileNameInserterButton.addActionListener(this);
        }
        return fileNameInserterButton;
    }

    /**
     * This method initializes the file name sequencer tool button.
     *
     *  @return the button
     */
    private JButton getFileNameSequencerButton() {

        final int x = 15;
        final int y = 225;
        final int w = 415;
        final int h = 22;

        if (fileNameSequencerButton == null) {
            fileNameSequencerButton = new JButton();
            fileNameSequencerButton.setBounds(x, y, w, h);
            fileNameSequencerButton.setText(Resources.RUN_FILE_NAME_SEQUENCER);
            fileNameSequencerButton.addActionListener(this);
        }
        return fileNameSequencerButton;
    }

    /**
     * This method initializes the jpeg metadata sorter and renamer tool button.
     *
     *  @return the button
     */
    private JButton getJpegMetadataSorterButton() {

        final int x = 15;
        final int y = 255;
        final int w = 415;
        final int h = 22;

        if (jpegMetadataSorterButton == null) {
            jpegMetadataSorterButton = new JButton();
            jpegMetadataSorterButton.setBounds(x, y, w, h);
            jpegMetadataSorterButton.setText(Resources.RUN_JPEG_METADATA_SORTER);
            jpegMetadataSorterButton.addActionListener(this);
        }
        return jpegMetadataSorterButton;
    }

    /**
     * Handler method for action performed events.
     *
     * @param event the action event
     */
    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == getExitMenuItem()) {
            exitAction();
        } else if (event.getSource() == getAboutMenuItem()) {
            aboutAction();
        } else if (event.getSource() == getDateChangerButton()) {
            dateChangerAction();
        } else if (event.getSource() == getDateShifterButton()) {
            dateShifterAction();
        } else if (event.getSource() == getTimeZoneShifterButton()) {
            timeZoneShifterAction();
        } else if (event.getSource() == getLowerCaseRenamerButton()) {
            lowerCaseRenamerAction();
        } else if (event.getSource() == getFileRenamerButton()) {
            fileRenamerAction();
        } else if (event.getSource() == getFileNameInserterButton()) {
            fileNameInserterAction();
        } else if (event.getSource() == getFileNameSequencerButton()) {
            fileNameSequencerAction();
        } else if (event.getSource() == getPutZeroInTimeInatorButton()) {
            putZeroInTimeInatorAction();
        } else if (event.getSource() == getJpegMetadataSorterButton()) {
            jpegMetadataSorterAction();
        }
    }

    /**
     * Method invoked when the date changer tool button is pressed.
     */
    private void dateChangerAction() {

        SwingUtilities.invokeLater(new DateChangerRunner());
        exitAction();
    }

    /**
     * Method invoked when the date shifter tool button is pressed.
     */
    private void dateShifterAction() {

        SwingUtilities.invokeLater(new DateShifterRunner());
        exitAction();
    }

    /**
     * Method invoked when the time zone shifter tool button is pressed.
     */
    private void timeZoneShifterAction() {

        SwingUtilities.invokeLater(new TimeZoneShifterRunner());
        exitAction();
    }

    /**
     * Method invoked when the lower case renamer tool button is pressed.
     */
    private void lowerCaseRenamerAction() {

        SwingUtilities.invokeLater(new LowerCaseRenamerRunner());
        exitAction();
    }

    /**
     * Method invoked when the file renamer tool button is pressed.
     */
    private void fileRenamerAction() {

        SwingUtilities.invokeLater(new FileRenamerRunner());
        exitAction();
    }

    /**
     * Method invoked when the file name inserter tool button is pressed.
     */
    private void fileNameInserterAction() {

        SwingUtilities.invokeLater(new FileNameInserterRunner());
        exitAction();
    }

    /**
     * Method invoked when the file name sequencer tool button is pressed.
     */
    private void fileNameSequencerAction() {

        SwingUtilities.invokeLater(new FileNameSequencerRunner());
        exitAction();
    }

    /**
     * Method invoked when the putZeroInTimeInator tool button is pressed.
     */
    private void putZeroInTimeInatorAction() {

        SwingUtilities.invokeLater(new PutZeroInTimeInatorRunner());
        exitAction();
    }

    /**
     * Method invoked when the jpeg metadata sorter and renamer tool button is pressed.
     */
    private void jpegMetadataSorterAction() {

        SwingUtilities.invokeLater(new JpegMetadataSorterRunner());
        exitAction();
    }

    /**
     * Empty handler for window activated events.
     *
     * @param event the window event
     */
    public void windowActivated(WindowEvent event) {
    }

    /**
     * Empty handler for window closed events.
     *
     * @param event the window event
     */
    public void windowClosed(WindowEvent event) {
    }

    /**
     * Handler for window closing events.
     *
     * @param event the window event
     */
    public void windowClosing(WindowEvent event) {

        dispose();
    }

    /**
     * Empty handler for window deactivated events.
     *
     * @param event the window event
     */
    public void windowDeactivated(WindowEvent event) {
    }

    /**
     * Empty handler for window deiconified events.
     *
     * @param event the window event
     */
    public void windowDeiconified(WindowEvent event) {
    }

    /**
     * Empty handler for window iconified events.
     *
     * @param event the window event
     */
    public void windowIconified(WindowEvent event) {
    }

    /**
     * Empty handler for window opened events.
     *
     * @param event the window event
     */
    public void windowOpened(WindowEvent event) {
    }
}
