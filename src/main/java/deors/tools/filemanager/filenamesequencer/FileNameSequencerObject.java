package deors.tools.filemanager.filenamesequencer;

/**
 * The FileNameSequencer object.
 *
 * @author deors
 * @version 1.0
 */
public class FileNameSequencerObject extends deors.core.sensible.SensibleObject {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = -6756336988070221729L;

    /**
     * The prefix.
     */
    @SuppressWarnings("PMD.SingularField")
    private final deors.core.sensible.SensibleString prefix;

    /**
     * The offset.
     */
    @SuppressWarnings("PMD.SingularField")
    private final deors.core.sensible.SensibleBigDecimal offset;

    /**
     * The padding.
     */
    @SuppressWarnings("PMD.SingularField")
    private final deors.core.sensible.SensibleBigDecimal padding;

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
     * The prefix order.
     */
    private static final int PREFIX_ORDER = 0;

    /**
     * The offset order.
     */
    private static final int OFFSET_ORDER = 1;

    /**
     * The padding order.
     */
    private static final int PADDING_ORDER = 2;

    /**
     * The root directory order.
     */
    private static final int ROOTDIR_ORDER = 3;

    /**
     * The recurse subdirectories order.
     */
    private static final int RECURSE_ORDER = 4;

    /**
     * The apply to files order.
     */
    private static final int APPLYTOFILES_ORDER = 5;

    /**
     * The apply to directories order.
     */
    private static final int APPLYTODIRECTORIES_ORDER = 6;

    /**
     * The filter files order.
     */
    private static final int FILTER_ORDER = 7;

    /**
     * The filter expression order.
     */
    private static final int FILTERREGEX_ORDER = 8;

    /**
     * Default constructor.
     */
    public FileNameSequencerObject() {

        super();

        prefix = new deors.core.sensible.SensibleString(256);
        offset = new deors.core.sensible.SensibleBigDecimal(3, 0);
        padding = new deors.core.sensible.SensibleBigDecimal(2, 0);
        rootDir = new deors.core.sensible.SensibleString(1024);
        recurse = new deors.core.sensible.SensibleBoolean(false);
        applyToFiles = new deors.core.sensible.SensibleBoolean(true);
        applyToDirectories = new deors.core.sensible.SensibleBoolean(false);
        filter = new deors.core.sensible.SensibleBoolean(false);
        filterRegex = new deors.core.sensible.SensibleString(1024);

        rootDir.setRequired(true);

        fields = new deors.core.sensible.SensibleDataType[] {
            prefix, offset, padding, rootDir, recurse, applyToFiles, applyToDirectories, filter, filterRegex};
        fieldNames = new String[] {
            "prefix", "offset", "padding", "rootDir", "recurse", "applyToFiles", "applyToDirectories", "filter", "filterRegex"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$

        addListeners();
    }

    /**
     * Returns the prefix value.
     *
     * @return the prefix value
     */
    public deors.core.sensible.SensibleString getPrefix() {

        return (deors.core.sensible.SensibleString) getField(PREFIX_ORDER);
    }

    /**
     * Returns the offset value.
     *
     * @return the offset value
     */
    public deors.core.sensible.SensibleBigDecimal getOffset() {

        return (deors.core.sensible.SensibleBigDecimal) getField(OFFSET_ORDER);
    }

    /**
     * Returns the padding value.
     *
     * @return the padding value
     */
    public deors.core.sensible.SensibleBigDecimal getPadding() {

        return (deors.core.sensible.SensibleBigDecimal) getField(PADDING_ORDER);
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
     * Changes the prefix value.
     *
     * @param prefix the new value for the prefix
     */
    public void setPrefix(deors.core.sensible.SensibleString prefix) {

        setField(PREFIX_ORDER, prefix);
    }

    /**
     * Changes the offset value.
     *
     * @param offset the new value for the offset
     */
    public void setOffset(deors.core.sensible.SensibleBigDecimal offset) {

        setField(OFFSET_ORDER, offset);
    }

    /**
     * Changes the padding value.
     *
     * @param padding the new value for the padding
     */
    public void setPadding(deors.core.sensible.SensibleBigDecimal padding) {

        setField(PADDING_ORDER, padding);
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
