package deors.tools.filemanager.jpegmetadatasorter;

import static deors.core.commons.StringToolkit.padLeft;
import static deors.core.commons.StringToolkit.replace;
import static deors.core.commons.StringToolkit.replaceMultiple;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import deors.core.commons.AbstractFileTool;
import deors.tools.filemanager.Resources;

/**
 * The JPEGSorterAndRenamer process.
 *
 * @author deors
 * @version 1.0
 */
public class JpegMetadataSorterProcess
extends AbstractFileTool {

    /**
     * String used to flag the name of a favorite picture.
     */
    private static final String FAVORITE_IN_NAME = "FAV"; //$NON-NLS-1$

    /**
     * JPG extension string (including the dot).
     */
    private static final String JPG_EXTENSION = ".jpg"; //$NON-NLS-1$

    /**
     * The space character.
     */
    private static final char SPACE = ' ';

    /**
     * The dot character.
     */
    private static final char DOT = '.';

    /**
     * The zero character.
     */
    private static final char ZERO_PAD = '0';

    /**
     * The name of the Exif metadata dir.
     */
    private static final String EXIF_METADATA_DIR = "Exif"; //$NON-NLS-1$

    /**
     * The id of the Exif tag for date/time original.
     */
    private static final int EXIF_DATETIME_ORIGINAL = 36867;

    /**
     * The id of the Exif tag for date/time digitized.
     */
    private static final int EXIF_DATETIME_DIGITIZED = 36868;

    /**
     * Main entry point for the tool when executed from command line.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        final int paramRootDir = 0;
        final int paramName = 1;
        final int paramOffset = 2;
        final int paramSort = 3;
        final int paramRename = 4;
        final int paramUpdate = 5;
        final int paramTest = 6;

        Logger logger = LoggerFactory.getLogger(JpegMetadataSorterProcess.class);

        if (args.length < paramTest) {
            logger.info(Resources.PRO_MISSING_PARAMETERS);
            return;
        }

        JpegMetadataSorterProcess process = new JpegMetadataSorterProcess(
            new File(args[paramRootDir]),
            args[paramName],
            Integer.parseInt(args[paramOffset]),
            Boolean.parseBoolean(args[paramSort]),
            Boolean.parseBoolean(args[paramRename]),
            Boolean.parseBoolean(args[paramUpdate]),
            Boolean.parseBoolean(args[paramTest]),
            true);

        logger.info(Resources.PRO_STARTED);

        int errors = process.doProcess();

        if (errors == 0) {
            logger.info(Resources.PRO_FINISHED_OK);
        } else {
            logger.info(Resources.PRO_FINISHED_ERROR, Integer.toString(errors));
        }
    }

    /**
     * Picture name.
     */
    private String name = Resources.BLANK;

    /**
     * Picture count offset.
     */
    private int offset;

    /**
     * Sort files by shot date.
     */
    private boolean sort = true;

    /**
     * Rename files.
     */
    private boolean rename = true;

    /**
     * Update modified date.
     */
    private boolean update = true;

    /**
     * Test only.
     */
    private boolean test;

    /**
     * File lists, indexed by containing directory.
     */
    private final Map<File, List<File>> fileMap = new HashMap<File, List<File>>();

    /**
     * Picture count.
     */
    private int count;

    /**
     * Last picture date.
     */
    private String lastDate = Resources.BLANK;

    /**
     * Time zone difference.
     */
    private final int timeZoneDiff = Integer.parseInt(System.getProperty("deors.timeZoneDiff", "0")); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * Date format for picture file names.
     */
    private static final SimpleDateFormat FILE_NAME_FORMAT = new SimpleDateFormat("yy-MM-dd", Locale.getDefault()); //$NON-NLS-1$

    /**
     * Date format for log messages.
     */
    private static final SimpleDateFormat LOG_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()); //$NON-NLS-1$

    /**
     * Process constructor.
     *
     * @param rootDir the root directory
     * @param name picture name
     * @param offset offset
     * @param sort sort files by shot date
     * @param rename rename files
     * @param update update modified date
     * @param test test only
     * @param recurse whether to recurse directories when searching for files to process
     */
    public JpegMetadataSorterProcess(File rootDir, String name, int offset, boolean sort,
        boolean rename, boolean update, boolean test,
        boolean recurse) {

        this(rootDir, name, offset, sort, rename, update, test,
            recurse, false, null);
    }

    /**
     * Process constructor.
     *
     * @param rootDir the root directory
     * @param name picture name
     * @param offset offset
     * @param sort sort files by shot date
     * @param rename rename files
     * @param update update modified date
     * @param test test only
     * @param recurse whether to recurse directories when searching for files to process
     * @param filter whether to filter out files by their name
     * @param filterRegex the regular expression used to filter out files by their name
     */
    public JpegMetadataSorterProcess(File rootDir, String name, int offset, boolean sort,
        boolean rename, boolean update, boolean test,
        boolean recurse, boolean filter, String filterRegex) {

        super(rootDir, recurse, true, false, filter, filterRegex);
        this.name = name;
        this.offset = offset;
        this.sort = sort;
        this.rename = rename;
        this.update = update;
        this.test = test;
    }

    /**
     * Executes actions over a directory.
     *
     * @param directory the directory that will be processed
     */
    @Override
    protected void applyActionsToDirectory(File directory) {

        // nothing to do
    }

    /**
     * Executes actions over a file.
     *
     * @param file the file that will be processed
     */
    @Override
    protected void applyActionsToFile(File file) {

        List<File> fileList = fileMap.get(file.getParentFile());
        if (fileList == null) {
            fileList = new ArrayList<File>();
            fileMap.put(file.getParentFile(), fileList);
        }
        fileList.add(file);
    }

    /**
     * The actions to be applied after the process ends.
     */
    @Override
    protected void doPostProcess() {

        Set<Entry<File, List<File>>> fileSet = fileMap.entrySet();
        for (Entry<File, List<File>> entry : fileSet) {

            List<File> fileList = entry.getValue();

            // if offset is set, starts counting from offset + 1
            // counter is reset for each processed directory
            if (offset == 0) {
                count = 0;
            } else {
                count = offset;
            }

            if (update) {
                logInfo(Resources.LOG_UPDATING_LAST_MODIFIED_DATES);
                for (File file : fileList) {
                    if (file.getName().toLowerCase().endsWith(JPG_EXTENSION)) {
                        updateLastModifiedDate(file);
                    }
                }
            }

            if (rename) {
                if (sort) {
                    logInfo(Resources.LOG_SORTING_FILES_BY_LAST_MODIFIED_DATE);
                } else {
                    logInfo(Resources.LOG_SORTING_FILES_BY_NAME);
                }

                Set<SortedFile> m = new HashSet<SortedFile>();
                Set<SortedFile> files = new TreeSet<SortedFile>();
                for (File file : fileList) {
                    if (!file.getName().toLowerCase().endsWith(JPG_EXTENSION)) {
                        continue;
                    }
                    SortedFile sf = new SortedFile();
                    sf.file = file;
                    // sort by date or by name depending on sort flag
                    if (sort) {
                        sf.lastModified = file.lastModified();
                        m.add(sf);
                    } else {
                        m.add(sf);
                    }
                }
                files.addAll(m);

                logInfo(Resources.LOG_RENAMING_FILES);
                for (SortedFile sortedFile : files) {
                    renameFile(sortedFile);
                }
            }
        }
    }

    /**
     * The actions to be applied before the process starts.
     */
    @Override
    protected void doPreProcess() {

        // nothing to do
    }

    /**
     * Updates the last modified date of a JPEG file using
     * the shot date in JPEG EXIF metadata.
     *
     * @param file the file to update
     *
     * @return whether an error occurred
     */
    private boolean updateLastModifiedDate(File file) {

        try {
            Metadata metadata = JpegMetadataReader.readMetadata(file);

            Iterator<Directory> dirs = metadata.getDirectories().iterator();
            while (dirs.hasNext()) {
                Directory dir = dirs.next();

                // when debug flag is on, show all metadata
                if (isDebugEnabled()) {
                    logDebug(replace(Resources.DEBUG_METADATA_DIRECTORY, dir.getName()));
                    @SuppressWarnings("rawtypes")
                    Iterator tags = dir.getTags().iterator();
                    while (tags.hasNext()) {
                        Tag tag = (Tag) tags.next();
                        logDebug(replaceMultiple(Resources.DEBUG_TAG_VALUE_TYPE,
                            new String[] {tag.getTagName(), tag.getDescription(), Integer.toString(tag.getTagType())}));
                    }
                }

                // the file last modified date is updated
                // using the picture shot date in metadata
                if (dir.getName().equals(EXIF_METADATA_DIR)) {
                    // fix for certain camera pictures not having the right time zone
                    //                    boolean fix = false;
                    //                    if (dir.containsTag(272)) {
                    //                        if (dir.getString(272).startsWith("KODAK")
                    //                            || dir.getString(272).startsWith("COOLPIX")) {
                    //                            fix = true;
                    //                        }
                    //                    }

                    Date d = null;
                    if (dir.containsTag(EXIF_DATETIME_ORIGINAL)) {
                        // tag Date/Time Original
                        d = dir.getDate(EXIF_DATETIME_ORIGINAL);
                    } else if (dir.containsTag(EXIF_DATETIME_DIGITIZED)) {
                        // tag Date/Time Digitized
                        d = dir.getDate(EXIF_DATETIME_DIGITIZED);
                    }
                    if (d == null) {
                        continue;
                    }

                    // fix for certain camera pictures not having the right time zone
                    //                    if (fix) {
                    //                        Calendar c = Calendar.getInstance();
                    //                        c.setTime(d);
                    //                        c.add(Calendar.HOUR_OF_DAY, -1);
                    //                        d = c.getTime();
                    //                    }

                    // adding the time zone diff
                    // if the system property is set
                    Calendar c = Calendar.getInstance();
                    c.setTime(d);
                    c.add(Calendar.HOUR_OF_DAY, timeZoneDiff);
                    d = c.getTime();

                    if (!test) {
                        file.setLastModified(d.getTime());
                    }
                    synchronized (LOG_FORMAT) {
                        logInfo(replaceMultiple(Resources.LOG_FILE_NEW_DATE,
                            new String[] {file.getName(), LOG_FORMAT.format(d)}));
                    }
                }
            }
            return true;
        } catch (JpegProcessingException jpe) {
            logError(replace(Resources.PRO_EXCEPTION, jpe.getMessage()));
            return false;
        } catch (IOException ioe) {
            logError(replace(Resources.PRO_EXCEPTION, ioe.getMessage()));
            return false;
        } catch (IllegalArgumentException iae) {
            logError(replace(Resources.PRO_EXCEPTION, iae.getMessage()));
            return false;
        }
    }

    /**
     * Renames the given file using the date, suggested name and actual count.
     *
     * @param sortedFile the file to rename
     */
    private void renameFile(SortedFile sortedFile) {

        File file = sortedFile.file;
        Calendar fileDate = Calendar.getInstance();
        fileDate.setTimeInMillis(file.lastModified());
        StringBuilder newFileName = new StringBuilder();
        synchronized (FILE_NAME_FORMAT) {
            String formattedDate = FILE_NAME_FORMAT.format(fileDate.getTime());
            newFileName.append(formattedDate);
            if (lastDate.length() != 0 && !formattedDate.equals(lastDate)) {
                lastDate = formattedDate;
                count = 0;
            }
        }
        newFileName.append(DOT);
        newFileName.append(padLeft(Integer.toString(++count), 3, ZERO_PAD));
        newFileName.append(DOT);

        // only adds a name to the picture if it did not have one already
        int extPos = file.getName().lastIndexOf(DOT);
        int desPos = file.getName().lastIndexOf(DOT, extPos - 1);
        if (desPos == -1) {
            newFileName.append(name);
            if (file.getName().contains(FAVORITE_IN_NAME)) {
                newFileName.append(SPACE);
                newFileName.append(FAVORITE_IN_NAME);
            }
        } else {
            newFileName.append(file.getName().substring(desPos + 1, extPos));
        }

        newFileName.append(JPG_EXTENSION);

        // renaming the file
        if (newFileName.toString().equals(file.getName())) {
            logInfo(replace(Resources.LOG_FILE_NAME_NO_CHANGE, file.getName()));
        } else {
            if (test) {
                if (new File(file.getParentFile(), newFileName.toString()).exists()) {
                    logInfo(replaceMultiple(Resources.LOG_FILE_NEW_NAME_EXISTS,
                        new String[] {file.getName(), newFileName.toString()}));
                } else {
                    logInfo(replaceMultiple(Resources.LOG_FILE_NEW_NAME,
                        new String[] {file.getName(), newFileName.toString()}));
                }
            } else {
                if (file.renameTo(new File(file.getParentFile(), newFileName.toString()))) {
                    logInfo(replaceMultiple(Resources.LOG_FILE_NEW_NAME,
                        new String[] {file.getName(), newFileName.toString()}));
                } else {
                    logError(replace(Resources.LOG_FILE_RENAME_ERROR, file.getName()));
                }
            }
        }
    }

    /**
     * Implementation of a comparable file. Files are compared using
     * their last modified date.
     *
     * @author deors
     * @version 1.0
     */
    private static final class SortedFile
    implements Comparable<SortedFile> {

        /**
         * The file.
         */
        private File file;

        /**
         * The last modified date.
         */
        private long lastModified;

        /**
         * Default constructor.
         */
        public SortedFile() {

            super();
        }

        /**
         * Calculates and returns the hash code for the file. Actually it
         * returns the last modified date downcasted to int.
         *
         * @return the hash code
         */
        public int hashCode() {

            return (int) lastModified;
        }

        /**
         * Compares the given object with this for equality and returns <code>true</code>
         * if both files have the same last modified date.
         *
         * @param o the comparison object
         * @return whether the given object and this have the same last modified date
         */
        public boolean equals(Object o) {

            if (!(o instanceof SortedFile)) {
                return false;
            }
            return this.compareTo((SortedFile) o) == 0;
        }

        /**
         * Compares the given object with this.
         *
         * @param o the comparison object
         * @return -1, 0 or 1 depending on what last modified date is the greatest
         */
        public int compareTo(SortedFile o) {

            long dif = lastModified - o.lastModified;
            if (dif > 1) {
                dif = 1;
            } else if (dif < -1) {
                dif = -1;
            }
            if (dif == 0) {
                dif = file.getName().compareTo(o.file.getName());
            }
            return (int) dif;
        }
    }
}
