package deors.tools.filemanager.timezoneshifter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("TimeZoneShifterProcess")
public class TimeZoneShifterProcessTest {

    private static final long ORIGINAL = 1_700_000_000_000L;

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("adds the time zone shift in hours")
    void shiftsHoursForward() throws Exception {

        Path file = write("photo.jpg");
        setMillis(file, ORIGINAL);

        int errors = new TimeZoneShifterProcess(
            2, tempDir.toFile(), false, true, false, false, null).doProcess();

        assertEquals(0, errors);
        assertSameSecond(file, shifted(ORIGINAL, 2));
    }

    @Test
    @DisplayName("subtracts a negative time zone shift")
    void shiftsHoursBackward() throws Exception {

        Path file = write("photo.jpg");
        setMillis(file, ORIGINAL);

        int errors = new TimeZoneShifterProcess(
            -3, tempDir.toFile(), false, true, false, false, null).doProcess();

        assertEquals(0, errors);
        assertSameSecond(file, shifted(ORIGINAL, -3));
    }

    @Test
    @DisplayName("does not enter subdirectories when recurse is false")
    void doesNotEnterSubdirectoriesWhenRecurseIsFalse() throws Exception {

        Path nested = Files.createDirectory(tempDir.resolve("nested"));
        Path inner = write(nested.resolve("inner.jpg"));
        Path rootFile = write("root.jpg");
        setMillis(inner, ORIGINAL);
        setMillis(rootFile, ORIGINAL);

        int errors = new TimeZoneShifterProcess(
            1, tempDir.toFile(), false, true, false, false, null).doProcess();

        assertEquals(0, errors);
        assertSameSecond(rootFile, shifted(ORIGINAL, 1));
        assertSameSecond(inner, ORIGINAL);
    }

    @Test
    @DisplayName("only touches files that match the filter")
    void appliesFilterRegex() throws Exception {

        Path keep = write("keep-me.jpg");
        Path skip = write("skip-me.jpg");
        setMillis(keep, ORIGINAL);
        setMillis(skip, ORIGINAL);

        int errors = new TimeZoneShifterProcess(
            1, tempDir.toFile(), false, true, false, true, "keep").doProcess();

        assertEquals(0, errors);
        assertSameSecond(keep, shifted(ORIGINAL, 1));
        assertSameSecond(skip, ORIGINAL);
    }

    @Test
    @DisplayName("reports an error when the root directory does not exist")
    void reportsErrorWhenRootDoesNotExist() {

        int errors = new TimeZoneShifterProcess(1, tempDir.resolve("missing").toFile(), false).doProcess();

        assertEquals(1, errors);
    }

    private Path write(String name) throws IOException {

        return write(tempDir.resolve(name));
    }

    private Path write(Path path) throws IOException {

        Files.writeString(path, "content", StandardCharsets.UTF_8);
        return path;
    }

    private static void setMillis(Path path, long millis) throws IOException {

        assertTrue(path.toFile().setLastModified(millis), () -> "could not set lastModified on " + path);
    }

    private static long shifted(long original, int hours) {

        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(original);
        time.add(Calendar.HOUR_OF_DAY, hours);
        return time.getTimeInMillis();
    }

    private static void assertSameSecond(Path path, long expectedMillis) throws IOException {

        long actual = Files.getLastModifiedTime(path).toMillis();
        assertEquals(expectedMillis / 1000, actual / 1000,
            () -> "expected " + expectedMillis + " but was " + actual + " for " + path);
    }
}
