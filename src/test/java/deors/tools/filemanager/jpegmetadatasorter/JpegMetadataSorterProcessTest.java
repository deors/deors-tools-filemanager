package deors.tools.filemanager.jpegmetadatasorter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("JpegMetadataSorterProcess")
public class JpegMetadataSorterProcessTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("renames a jpeg using date, count and the given name")
    void renamesJpegUsingDateAndName() throws Exception {

        Path file = write("camera.jpg", "img");
        setMillis(file, date(2024, Calendar.MARCH, 15, 12, 0, 0));

        int errors = process(tempDir, "holiday", 0, false, true, false, false, false).doProcess();

        assertEquals(0, errors);
        assertFalse(Files.exists(tempDir.resolve("camera.jpg")));
        assertEquals("img", read(expectedName(2024, Calendar.MARCH, 15, 1, "holiday")));
    }

    @Test
    @DisplayName("starts the count after the given offset")
    void startsCountAfterOffset() throws Exception {

        Path file = write("camera.jpg", "img");
        setMillis(file, date(2024, Calendar.MARCH, 15, 12, 0, 0));

        int errors = process(tempDir, "holiday", 5, false, true, false, false, false).doProcess();

        assertEquals(0, errors);
        assertEquals("img", read(expectedName(2024, Calendar.MARCH, 15, 6, "holiday")));
    }

    @Test
    @DisplayName("keeps an existing description between the last two dots")
    void keepsExistingDescription() throws Exception {

        Path file = write("old.desc.jpg", "img");
        setMillis(file, date(2024, Calendar.MARCH, 15, 12, 0, 0));

        int errors = process(tempDir, "holiday", 0, false, true, false, false, false).doProcess();

        assertEquals(0, errors);
        assertEquals("img", read(expectedName(2024, Calendar.MARCH, 15, 1, "desc")));
    }

    @Test
    @DisplayName("flags favorite pictures in the generated name")
    void flagsFavoritePictures() throws Exception {

        Path file = write("shotFAV.jpg", "img");
        setMillis(file, date(2024, Calendar.MARCH, 15, 12, 0, 0));

        int errors = process(tempDir, "holiday", 0, false, true, false, false, false).doProcess();

        assertEquals(0, errors);
        assertEquals("img", read(expectedName(2024, Calendar.MARCH, 15, 1, "holiday FAV")));
    }

    @Test
    @DisplayName("ignores files that are not jpeg when renaming")
    void ignoresNonJpegFiles() throws Exception {

        write("notes.txt", "keep");
        Path jpeg = write("camera.jpg", "img");
        setMillis(jpeg, date(2024, Calendar.MARCH, 15, 12, 0, 0));

        int errors = process(tempDir, "holiday", 0, false, true, false, false, false).doProcess();

        assertEquals(0, errors);
        assertEquals("keep", read("notes.txt"));
        assertTrue(Files.exists(tempDir.resolve(expectedName(2024, Calendar.MARCH, 15, 1, "holiday"))));
    }

    @Test
    @DisplayName("does not rename files in test mode")
    void doesNotRenameInTestMode() throws Exception {

        Path file = write("camera.jpg", "img");
        setMillis(file, date(2024, Calendar.MARCH, 15, 12, 0, 0));

        int errors = process(tempDir, "holiday", 0, false, true, false, true, false).doProcess();

        assertEquals(0, errors);
        assertEquals("img", read("camera.jpg"));
        assertEquals(Set.of("camera.jpg"), fileNames(tempDir));
    }

    @Test
    @DisplayName("does nothing when rename and update are disabled")
    void doesNothingWhenRenameAndUpdateAreDisabled() throws Exception {

        write("camera.jpg", "img");

        int errors = process(tempDir, "holiday", 0, false, false, false, false, false).doProcess();

        assertEquals(0, errors);
        assertEquals("img", read("camera.jpg"));
    }

    @Test
    @DisplayName("counts an error when updating metadata of a file that is not a jpeg")
    void reportsErrorWhenUpdatingInvalidJpeg() throws Exception {

        write("camera.jpg", "not a jpeg");

        int errors = process(tempDir, "holiday", 0, false, false, true, false, false).doProcess();

        assertTrue(errors > 0);
        assertEquals("not a jpeg", read("camera.jpg"));
    }

    @Test
    @DisplayName("sorts files by last modified date before renaming")
    void sortsByLastModifiedDate() throws Exception {

        Path later = write("later.jpg", "b");
        Path earlier = write("earlier.jpg", "a");
        setMillis(later, date(2024, Calendar.MARCH, 15, 18, 0, 0));
        setMillis(earlier, date(2024, Calendar.MARCH, 15, 8, 0, 0));

        int errors = process(tempDir, "holiday", 0, true, true, false, false, false).doProcess();

        assertEquals(0, errors);
        assertEquals("a", read(expectedName(2024, Calendar.MARCH, 15, 1, "holiday")));
        assertEquals("b", read(expectedName(2024, Calendar.MARCH, 15, 2, "holiday")));
    }

    @Test
    @DisplayName("does not enter subdirectories when recurse is false")
    void doesNotEnterSubdirectoriesWhenRecurseIsFalse() throws Exception {

        Path nested = Files.createDirectory(tempDir.resolve("nested"));
        Path inner = write(nested.resolve("inner.jpg"), "inner");
        Path root = write("root.jpg", "root");
        long stamp = date(2024, Calendar.MARCH, 15, 12, 0, 0);
        setMillis(inner, stamp);
        setMillis(root, stamp);

        int errors = process(tempDir, "holiday", 0, false, true, false, false, false).doProcess();

        assertEquals(0, errors);
        assertEquals("root", read(expectedName(2024, Calendar.MARCH, 15, 1, "holiday")));
        assertTrue(Files.exists(nested.resolve("inner.jpg")));
    }

    @Test
    @DisplayName("renames jpeg files in subdirectories when recurse is true")
    void renamesInSubdirectoriesWhenRecursing() throws Exception {

        Path nested = Files.createDirectory(tempDir.resolve("nested"));
        Path inner = write(nested.resolve("inner.jpg"), "inner");
        setMillis(inner, date(2024, Calendar.MARCH, 15, 12, 0, 0));

        int errors = process(tempDir, "holiday", 0, false, true, false, false, true).doProcess();

        assertEquals(0, errors);
        assertEquals("inner", Files.readString(
            nested.resolve(expectedName(2024, Calendar.MARCH, 15, 1, "holiday")), StandardCharsets.UTF_8));
    }

    @Test
    @DisplayName("reports an error when the root directory does not exist")
    void reportsErrorWhenRootDoesNotExist() {

        int errors = process(tempDir.resolve("missing"), "holiday", 0, false, true, false, false, false).doProcess();

        assertEquals(1, errors);
    }

    private JpegMetadataSorterProcess process(Path root, String name, int offset, boolean sort,
            boolean rename, boolean update, boolean test, boolean recurse) {

        return new JpegMetadataSorterProcess(
            root.toFile(), name, offset, sort, rename, update, test, recurse);
    }

    private Path write(String name, String content) throws IOException {

        return write(tempDir.resolve(name), content);
    }

    private Path write(Path path, String content) throws IOException {

        Files.writeString(path, content, StandardCharsets.UTF_8);
        return path;
    }

    private String read(String name) throws IOException {

        return Files.readString(tempDir.resolve(name), StandardCharsets.UTF_8);
    }

    private static void setMillis(Path path, long millis) throws IOException {

        assertTrue(path.toFile().setLastModified(millis), () -> "could not set lastModified on " + path);
    }

    private static long date(int year, int month, int day, int hour, int minute, int second) {

        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(year, month, day, hour, minute, second);
        return time.getTimeInMillis();
    }

    private static String expectedName(int year, int month, int day, int count, String name) {

        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(year, month, day, 12, 0, 0);
        String datePart = new SimpleDateFormat("yy-MM-dd", Locale.getDefault()).format(time.getTime());
        return String.format("%s.%03d.%s.jpg", datePart, count, name);
    }

    private Set<String> fileNames(Path dir) throws IOException {

        Set<String> names = new HashSet<String>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                if (Files.isRegularFile(path)) {
                    names.add(path.getFileName().toString());
                }
            }
        }
        return names;
    }
}
