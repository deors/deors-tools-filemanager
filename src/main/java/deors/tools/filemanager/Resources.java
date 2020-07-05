package deors.tools.filemanager;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Class used to access configuration settings and messages.
 *
 * @author deors
 * @version 1.0
 */
public final class Resources {

    /**
     * The resource bundle name.
     */
    private static final String BUNDLE_NAME = "deors.tools.filemanager.resources"; //$NON-NLS-1$

    /**
     * The resource bundle.
     */
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * A blank string.
     */
    public static final String BLANK = ""; //$NON-NLS-1$

    /**
     * New line string for the text area.
     */
    public static final String NEW_LINE = "\n"; //$NON-NLS-1$

    /**
     * Worker state property name.
     */
    public static final String STATE = "state"; //$NON-NLS-1$

    /**
     * An image path in resource bundle.
     */
    public static final String IMG_NEW = getString("img.new"); //$NON-NLS-1$

    /**
     * An image path in resource bundle.
     */
    public static final String IMG_OPEN = getString("img.open"); //$NON-NLS-1$

    /**
     * An image path in resource bundle.
     */
    public static final String IMG_SAVE = getString("img.save"); //$NON-NLS-1$

    /**
     * An image path in resource bundle.
     */
    public static final String IMG_EXIT = getString("img.exit"); //$NON-NLS-1$

    /**
     * An image path in resource bundle.
     */
    public static final String IMG_HELP = getString("img.help"); //$NON-NLS-1$

    /**
     * An image path in resource bundle.
     */
    public static final String IMG_PIN = getString("img.pin"); //$NON-NLS-1$

    /**
     * An image path in resource bundle.
     */
    public static final String IMG_CONSOLE = getString("img.console"); //$NON-NLS-1$

    /**
     * An image path in resource bundle.
     */
    public static final String IMG_DEORS = getString("img.deors"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FILE = getString("common.file"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String NEW = getString("common.new"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String OPEN = getString("common.open"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String SAVE = getString("common.save"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String EXIT = getString("common.exit"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String HELP = getString("common.help"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String RUN = getString("common.run"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String PIN = getString("common.pin"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String CONSOLE = getString("common.console"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String WAIT = getString("common.wait"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String START = getString("common.start"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String CONFIRM_ABORT = getString("common.confirmAbort"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String SURE = getString("common.sure"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FINISHED_OK = getString("common.finishedOk"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FINISHED_ERROR = getString("common.finishedError"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String EXCEPTION = getString("common.exception"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String PRO_STARTED = getString("process.started"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String PRO_FINISHED_OK = getString("process.finishedOk"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String PRO_FINISHED_ERROR = getString("process.finishedError"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String PRO_EXCEPTION = getString("process.exception"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String PRO_MISSING_PARAMETERS = getString("process.missingParameters"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String SUITE_TITLE = getString("suite.title"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String SUITE_ABOUT = getString("suite.about"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String SUITE_ABOUT_TEXT = getString("suite.aboutText"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String RUN_DATE_CHANGER = getString("run.dateChanger"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String RUN_DATE_SHIFTER = getString("run.dateShifter"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String RUN_TIME_ZONE_SHIFTER = getString("run.timeZoneShifter"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String RUN_LOWER_CASE_RENAMER = getString("run.lowerCaseRenamer"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String RUN_FILE_RENAMER = getString("run.fileRenamer"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String RUN_FILE_NAME_INSERTER = getString("run.fileNameInserter"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String RUN_FILE_NAME_SEQUENCER = getString("run.fileNameSequencer"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String RUN_PUT_ZERO_IN_TIME_INATOR = getString("run.putZeroInTimeInator"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String RUN_JPEG_METADATA_SORTER = getString("run.jpegMetadataSorter"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String DATECHANGER_TITLE = getString("dateChanger.title"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String DATECHANGER_ABOUT = getString("dateChanger.about"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String DATECHANGER_ABOUT_TEXT = getString("dateChanger.aboutText"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String DATESHIFTER_TITLE = getString("dateShifter.title"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String DATESHIFTER_ABOUT = getString("dateShifter.about"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String DATESHIFTER_ABOUT_TEXT = getString("dateShifter.aboutText"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FILERENAMER_TITLE = getString("fileRenamer.title"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FILERENAMER_ABOUT = getString("fileRenamer.about"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FILERENAMER_ABOUT_TEXT = getString("fileRenamer.aboutText"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String LOWERCASERENAMER_TITLE = getString("lowerCaseRenamer.title"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String LOWERCASERENAMER_ABOUT = getString("lowerCaseRenamer.about"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String LOWERCASERENAMER_ABOUT_TEXT = getString("lowerCaseRenamer.aboutText"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String TIMEZONESHIFTER_TITLE = getString("timeZoneShifter.title"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String TIMEZONESHIFTER_ABOUT = getString("timeZoneShifter.about"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String TIMEZONESHIFTER_ABOUT_TEXT = getString("timeZoneShifter.aboutText"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FILENAMEINSERTER_TITLE = getString("fileNameInserter.title"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FILENAMEINSERTER_ABOUT = getString("fileNameInserter.about"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FILENAMEINSERTER_ABOUT_TEXT = getString("fileNameInserter.aboutText"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FILENAMESEQUENCER_TITLE = getString("fileNameSequencer.title"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FILENAMESEQUENCER_ABOUT = getString("fileNameSequencer.about"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FILENAMESEQUENCER_ABOUT_TEXT = getString("fileNameSequencer.aboutText"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String PUTZEROINTIMEINATOR_TITLE = getString("putZeroInTimeInator.title"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String PUTZEROINTIMEINATOR_ABOUT = getString("putZeroInTimeInator.about"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String PUTZEROINTIMEINATOR_ABOUT_TEXT = getString("putZeroInTimeInator.aboutText"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String JPEG_METADATA_SORTER_TITLE = getString("jpegMetadataSorter.title"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String JPEG_METADATA_SORTER_ABOUT = getString("jpegMetadataSorter.about"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String JPEG_METADATA_SORTER_ABOUT_TEXT = getString("jpegMetadataSorter.aboutText"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_ROOTDIR = getString("field.rootDir"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_RECURSE = getString("field.recurse"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_APPLYTOFILES = getString("field.applyToFiles"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_APPLYTODIRECTORIES = getString("field.applyToDirectories"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_FILTER = getString("field.filter"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_FILTERREGEX = getString("field.filterRegex"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_NEWDATE = getString("field.newDate"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_SHIFT = getString("field.shift"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_REGEX = getString("field.regex"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_REPLACEMENT = getString("field.replacement"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_TZSHIFT = getString("field.tzShift"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_INSERTPOS = getString("field.insertPos"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_INSERTTEXT = getString("field.insertText"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_PREFIX = getString("field.prefix"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_OFFSET = getString("field.offset"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_PADDING = getString("field.padding"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_NAME = getString("field.name"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_SORT = getString("field.sort"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_RENAME = getString("field.rename"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_UPDATE = getString("field.update"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String FIELD_TEST = getString("field.test"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String LOG_DATE_MODIFIED = getString("log.dateModified"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String LOG_ERROR_MODIFYING_DATE = getString("log.errorModifyingDate"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String LOG_FILE_RENAMED = getString("log.fileRenamed"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String LOG_ERROR_RENAMING_FILE = getString("log.errorRenamingFile"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String LOG_FILE_NAME_NO_CHANGE = getString("log.fileNameNoChange"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String LOG_FILE_NEW_DATE = getString("log.fileNewDate"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String LOG_FILE_NEW_NAME = getString("log.fileNewName"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String LOG_FILE_NEW_NAME_EXISTS = getString("log.fileNewNameExists"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String LOG_FILE_RENAME_ERROR = getString("log.fileRenameError"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String LOG_RENAMING_FILES = getString("log.renamingFiles"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String LOG_SORTING_FILES_BY_LAST_MODIFIED_DATE = getString("log.sortingFilesByLastModifiedDate"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String LOG_SORTING_FILES_BY_NAME = getString("log.sortingFilesByName"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String LOG_UPDATING_LAST_MODIFIED_DATES = getString("log.updatingLastModifiedDates"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String DEBUG_METADATA_DIRECTORY = getString("debug.metadataDirectory"); //$NON-NLS-1$

    /**
     * A text string in resource bundle.
     */
    public static final String DEBUG_TAG_VALUE_TYPE = getString("debug.tagValueType"); //$NON-NLS-1$

    /**
     * Default constructor.
     */
    private Resources() {

        super();
    }

    /**
     * Returns a string in the resource bundle.
     *
     * @param key the key of the string
     *
     * @return the value of the string
     */
    static String getString(String key) {

        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }
}
