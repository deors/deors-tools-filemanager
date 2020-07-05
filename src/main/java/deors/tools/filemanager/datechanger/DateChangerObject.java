package deors.tools.filemanager.datechanger;

/**
 * The DateChanger object.
 *
 * @author deors
 * @version 1.0
 */
public class DateChangerObject extends deors.core.sensible.SensibleObject {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = 3820325081547445843L;

    /**
     * The new date and time.
     */
    @SuppressWarnings("PMD.SingularField")
    private final deors.core.sensible.SensibleDateTime newDate;

    /**
     * The root directory.
     */
    @SuppressWarnings("PMD.SingularField")
    private final deors.core.sensible.SensibleString rootDir;

    /**
     * The recurse subdirectories.
     */
    @SuppressWarnings("PMD.SingularField")
    private final deors.core.sensible.SensibleBoolean recurse;

    /**
     * The apply to files.
     */
    @SuppressWarnings("PMD.SingularField")
    private final deors.core.sensible.SensibleBoolean applyToFiles;

    /**
     * The apply to directories.
     */
    @SuppressWarnings("PMD.SingularField")
    private final deors.core.sensible.SensibleBoolean applyToDirectories;

    /**
     * The filter files.
     */
    @SuppressWarnings("PMD.SingularField")
    private final deors.core.sensible.SensibleBoolean filter;

    /**
     * The filter expression.
     */
    @SuppressWarnings("PMD.SingularField")
    private final deors.core.sensible.SensibleString filterRegex;

    /**
     * The new date and time order.
     */
    private static final int NEWDATE_ORDER = 0;

    /**
     * The root directory order.
     */
    private static final int ROOTDIR_ORDER = 1;

    /**
     * The recurse subdirectories order.
     */
    private static final int RECURSE_ORDER = 2;

    /**
     * The apply to files order.
     */
    private static final int APPLYTOFILES_ORDER = 3;

    /**
     * The apply to directories order.
     */
    private static final int APPLYTODIRECTORIES_ORDER = 4;

    /**
     * The filter files order.
     */
    private static final int FILTER_ORDER = 5;

    /**
     * The filter expression order.
     */
    private static final int FILTERREGEX_ORDER = 6;

    /**
     * Default constructor.
     */
    public DateChangerObject() {

        super();

        newDate = new deors.core.sensible.SensibleDateTime(true, true);
        rootDir = new deors.core.sensible.SensibleString(1024);
        recurse = new deors.core.sensible.SensibleBoolean(false);
        applyToFiles = new deors.core.sensible.SensibleBoolean(true);
        applyToDirectories = new deors.core.sensible.SensibleBoolean(false);
        filter = new deors.core.sensible.SensibleBoolean(false);
        filterRegex = new deors.core.sensible.SensibleString(1024);

        newDate.setRequired(true);
        rootDir.setRequired(true);

        fields = new deors.core.sensible.SensibleDataType[] {
            newDate, rootDir, recurse, applyToFiles, applyToDirectories, filter, filterRegex};
        fieldNames = new String[] {
            "newDate", "rootDir", "recurse", "applyToFiles", "applyToDirectories", "filter", "filterRegex"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$

        addListeners();
    }

    /**
     * Returns the new date and time value.
     *
     * @return the new date and time value
     */
    public deors.core.sensible.SensibleDateTime getNewDate() {

        return (deors.core.sensible.SensibleDateTime) getField(NEWDATE_ORDER);
    }

    /**
     * Returns the root directory value.
     *
     * @return the root directory value
     */
    public deors.core.sensible.SensibleString getRootDir() {

        return (deors.core.sensible.SensibleString) getField(ROOTDIR_ORDER);
    }

    /**
     * Returns the recurse subdirectories value.
     *
     * @return the recurse subdirectories value
     */
    public deors.core.sensible.SensibleBoolean getRecurse() {

        return (deors.core.sensible.SensibleBoolean) getField(RECURSE_ORDER);
    }

    /**
     * Returns the apply to files value.
     *
     * @return the apply to files value
     */
    public deors.core.sensible.SensibleBoolean getApplyToFiles() {

        return (deors.core.sensible.SensibleBoolean) getField(APPLYTOFILES_ORDER);
    }

    /**
     * Returns the apply to directories value.
     *
     * @return the apply to directories value
     */
    public deors.core.sensible.SensibleBoolean getApplyToDirectories() {

        return (deors.core.sensible.SensibleBoolean) getField(APPLYTODIRECTORIES_ORDER);
    }

    /**
     * Returns the filter files value.
     *
     * @return the filter files value
     */
    public deors.core.sensible.SensibleBoolean getFilter() {

        return (deors.core.sensible.SensibleBoolean) getField(FILTER_ORDER);
    }

    /**
     * Returns the filter expression value.
     *
     * @return the filter expression value
     */
    public deors.core.sensible.SensibleString getFilterRegex() {

        return (deors.core.sensible.SensibleString) getField(FILTERREGEX_ORDER);
    }

    /**
     * Changes the new date and time value.
     *
     * @param newDate the new value for the new date and time
     */
    public void setNewDate(deors.core.sensible.SensibleDateTime newDate) {

        setField(NEWDATE_ORDER, newDate);
    }

    /**
     * Changes the root directory value.
     *
     * @param rootDir the new value for the root directory
     */
    public void setRootDir(deors.core.sensible.SensibleString rootDir) {

        setField(ROOTDIR_ORDER, rootDir);
    }

    /**
     * Changes the recurse subdirectories value.
     *
     * @param recurse the new value for the recurse subdirectories
     */
    public void setRecurse(deors.core.sensible.SensibleBoolean recurse) {

        setField(RECURSE_ORDER, recurse);
    }

    /**
     * Changes the apply to files value.
     *
     * @param applyToFiles the new value for the apply to files
     */
    public void setApplyToFiles(deors.core.sensible.SensibleBoolean applyToFiles) {

        setField(APPLYTOFILES_ORDER, applyToFiles);
    }

    /**
     * Changes the apply to directories value.
     *
     * @param applyToDirectories the new value for the apply to directories
     */
    public void setApplyToDirectories(deors.core.sensible.SensibleBoolean applyToDirectories) {

        setField(APPLYTODIRECTORIES_ORDER, applyToDirectories);
    }

    /**
     * Changes the filter files value.
     *
     * @param filter the new value for the filter files
     */
    public void setFilter(deors.core.sensible.SensibleBoolean filter) {

        setField(FILTER_ORDER, filter);
    }

    /**
     * Changes the filter expression value.
     *
     * @param filterRegex the new value for the filter expression
     */
    public void setFilterRegex(deors.core.sensible.SensibleString filterRegex) {

        setField(FILTERREGEX_ORDER, filterRegex);
    }
}
