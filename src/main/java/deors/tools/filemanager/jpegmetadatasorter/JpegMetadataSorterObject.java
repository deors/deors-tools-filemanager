package deors.tools.filemanager.jpegmetadatasorter;

/**
 * The JPEGSorterAndRenamer object.
 *
 * @author deors
 * @version 1.0
 */
public class JpegMetadataSorterObject extends deors.core.sensible.SensibleObject {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = -1872314515062972406L;

    /**
     * The root directory.
     */
    @SuppressWarnings("PMD.SingularField")
    private final deors.core.sensible.SensibleString rootDir;

    /**
     * The picture name.
     */
    @SuppressWarnings("PMD.SingularField")
    private final deors.core.sensible.SensibleString name;

    /**
     * The offset.
     */
    @SuppressWarnings("PMD.SingularField")
    private final deors.core.sensible.SensibleBigDecimal offset;

    /**
     * The sort files by shot date.
     */
    @SuppressWarnings("PMD.SingularField")
    private final deors.core.sensible.SensibleBoolean sort;

    /**
     * The rename files.
     */
    @SuppressWarnings("PMD.SingularField")
    private final deors.core.sensible.SensibleBoolean rename;

    /**
     * The update modified date.
     */
    @SuppressWarnings("PMD.SingularField")
    private final deors.core.sensible.SensibleBoolean update;

    /**
     * The test only.
     */
    @SuppressWarnings("PMD.SingularField")
    private final deors.core.sensible.SensibleBoolean test;

    /**
     * The recurse subdirectories.
     */
    @SuppressWarnings("PMD.SingularField")
    private final deors.core.sensible.SensibleBoolean recurse;

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
     * The root Directory order.
     */
    private static final int ROOTDIR_ORDER = 0;

    /**
     * The picture name order.
     */
    private static final int NAME_ORDER = 1;

    /**
     * The offset order.
     */
    private static final int OFFSET_ORDER = 2;

    /**
     * The sort files by shot date order.
     */
    private static final int SORT_ORDER = 3;

    /**
     * The rename files order.
     */
    private static final int RENAME_ORDER = 4;

    /**
     * The update modified date order.
     */
    private static final int UPDATE_ORDER = 5;

    /**
     * The test only order.
     */
    private static final int TEST_ORDER = 6;

    /**
     * The recurse subdirectories order.
     */
    private static final int RECURSE_ORDER = 7;

    /**
     * The filter files order.
     */
    private static final int FILTER_ORDER = 8;

    /**
     * The filter expression order.
     */
    private static final int FILTERREGEX_ORDER = 9;

    /**
     * Default constructor.
     */
    public JpegMetadataSorterObject() {

        super();

        rootDir = new deors.core.sensible.SensibleString(1024);
        name = new deors.core.sensible.SensibleString(256);
        offset = new deors.core.sensible.SensibleBigDecimal(3, 0);
        sort = new deors.core.sensible.SensibleBoolean(true);
        rename = new deors.core.sensible.SensibleBoolean(true);
        update = new deors.core.sensible.SensibleBoolean(true);
        test = new deors.core.sensible.SensibleBoolean(false);
        recurse = new deors.core.sensible.SensibleBoolean(false);
        filter = new deors.core.sensible.SensibleBoolean(false);
        filterRegex = new deors.core.sensible.SensibleString(1024);

        rootDir.setRequired(true);
        name.setRequired(true);

        fields = new deors.core.sensible.SensibleDataType[] {
            rootDir, name, offset, sort, rename, update, test, recurse, filter, filterRegex};
        fieldNames = new String[] {
            "rootDir", "name", "offset", "sort", "rename", "update", "test", "recurse", "filter", "filterRegex"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$

        addListeners();
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
     * Returns the picture name value.
     *
     * @return the picture name value
     */
    public deors.core.sensible.SensibleString getName() {

        return (deors.core.sensible.SensibleString) getField(NAME_ORDER);
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
     * Returns the sort files by shot date value.
     *
     * @return the sort files by shot date value
     */
    public deors.core.sensible.SensibleBoolean getSort() {

        return (deors.core.sensible.SensibleBoolean) getField(SORT_ORDER);
    }

    /**
     * Returns the rename files value.
     *
     * @return the rename files value
     */
    public deors.core.sensible.SensibleBoolean getRename() {

        return (deors.core.sensible.SensibleBoolean) getField(RENAME_ORDER);
    }

    /**
     * Returns the update modified date value.
     *
     * @return the update modified date value
     */
    public deors.core.sensible.SensibleBoolean getUpdate() {

        return (deors.core.sensible.SensibleBoolean) getField(UPDATE_ORDER);
    }

    /**
     * Returns the test only value.
     *
     * @return the test only value
     */
    public deors.core.sensible.SensibleBoolean getTest() {

        return (deors.core.sensible.SensibleBoolean) getField(TEST_ORDER);
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
     * Changes the root directory value.
     *
     * @param rootDir the new value for the root directory
     */
    public void setRootDir(deors.core.sensible.SensibleString rootDir) {

        setField(ROOTDIR_ORDER, rootDir);
    }

    /**
     * Changes the picture name value.
     *
     * @param name the new value for the picture name
     */
    public void setName(deors.core.sensible.SensibleString name) {

        setField(NAME_ORDER, name);
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
     * Changes the sort files by shot date value.
     *
     * @param sort the new value for the sort files by shot date
     */
    public void setSort(deors.core.sensible.SensibleBoolean sort) {

        setField(SORT_ORDER, sort);
    }

    /**
     * Changes the rename files value.
     *
     * @param rename the new value for the rename files
     */
    public void setRename(deors.core.sensible.SensibleBoolean rename) {

        setField(RENAME_ORDER, rename);
    }

    /**
     * Changes the update modified date value.
     *
     * @param update the new value for the update modified date
     */
    public void setUpdate(deors.core.sensible.SensibleBoolean update) {

        setField(UPDATE_ORDER, update);
    }

    /**
     * Changes the test only value.
     *
     * @param test the new value for the test only
     */
    public void setTest(deors.core.sensible.SensibleBoolean test) {

        setField(TEST_ORDER, test);
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
