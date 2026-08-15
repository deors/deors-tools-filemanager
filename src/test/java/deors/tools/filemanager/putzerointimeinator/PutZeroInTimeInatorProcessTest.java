package deors.tools.filemanager.putzerointimeinator;

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

@DisplayName("PutZeroInTimeInatorProcess")
public class PutZeroInTimeInatorProcessTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("clears the time of day and keeps the calendar date")
    void zerosTimeOfDay() throws Exception {

        Path file = write("photo.jpg");
        Calendar original = calendar(2024, Calendar.JUNE, 10, 18, 45, 30, 500);
        setMillis(file, original.getTimeInMillis());

        int errors = new PutZeroInTimeInatorProcess(
            tempDir.toFile(), false, true, false, false, null).doProcess();

        assertEquals(0, errors);
        Calendar expected = calendar(2024, Calendar.JUNE, 10, 0, 0, 0, 0);
        assertSameSecond(file, expected.getTimeInMillis());
    }

    @Test
    @DisplayName("does not enter subdirectories when recurse is false")
    void doesNotEnterSubdirectoriesWhenRecurseIsFalse() throws Exception {

        Path nested = Files.createDirectory(tempDir.resolve("nested"));
        Path inner = write(nested.resolve("inner.jpg"));
        Path rootFile = write("root.jpg");
        Calendar original = calendar(2024, Calendar.MARCH, 1, 9, 15, 0, 0);
        setMillis(inner, original.getTimeInMillis());
        setMillis(rootFile, original.getTimeInMillis());

        int errors = new PutZeroInTimeInatorProcess(
            tempDir.toFile(), false, true, false, false, null).doProcess();

        assertEquals(0, errors);
        assertSameSecond(rootFile, calendar(2024, Calendar.MARCH, 1, 0, 0, 0, 0).getTimeInMillis());
        assertSameSecond(inner, original.getTimeInMillis());
    }

    @Test
    @DisplayName("only touches files that match the filter")
    void appliesFilterRegex() throws Exception {

        Path keep = write("keep-me.jpg");
        Path skip = write("skip-me.jpg");
        Calendar original = calendar(2024, Calendar.JANUARY, 2, 11, 22, 33, 0);
        setMillis(keep, original.getTimeInMillis());
        setMillis(skip, original.getTimeInMillis());

        int errors = new PutZeroInTimeInatorProcess(
            tempDir.toFile(), false, true, false, true, "keep").doProcess();

        assertEquals(0, errors);
        assertSameSecond(keep, calendar(2024, Calendar.JANUARY, 2, 0, 0, 0, 0).getTimeInMillis());
        assertSameSecond(skip, original.getTimeInMillis());
    }

    @Test
    @DisplayName("reports an error when the root directory does not exist")
    void reportsErrorWhenRootDoesNotExist() {

        int errors = new PutZeroInTimeInatorProcess(tempDir.resolve("missing").toFile(), false).doProcess();

        assertEquals(1, errors);
    }

    private Path write(String name) throws IOException {

        return write(tempDir.resolve(name));
    }

    private Path write(Path path) throws IOException {

        Files.writeString(path, "content", StandardCharsets.UTF_8);
        return path;
    }

    private static Calendar calendar(int year, int month, int day, int hour, int minute, int second, int millis) {

        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(year, month, day, hour, minute, second);
        time.set(Calendar.MILLISECOND, millis);
        return time;
    }

    private static void setMillis(Path path, long millis) throws IOException {

        assertTrue(path.toFile().setLastModified(millis), () -> "could not set lastModified on " + path);
    }

    private static void assertSameSecond(Path path, long expectedMillis) throws IOException {

        long actual = Files.getLastModifiedTime(path).toMillis();
        assertEquals(expectedMillis / 1000, actual / 1000,
            () -> "expected " + expectedMillis + " but was " + actual + " for " + path);
    }
}
