package deors.tools.filemanager.filenameinserter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("FileNameInserterProcess")
public class FileNameInserterProcessTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("inserts text at the beginning of the file name")
    void insertsAtTheBeginning() throws Exception {

        write("photo.jpg", "img");

        int errors = new FileNameInserterProcess(
            0, "trip_", tempDir.toFile(), false, true, false, false, null).doProcess();

        assertEquals(0, errors);
        assertFalse(Files.exists(tempDir.resolve("photo.jpg")));
        assertEquals("img", read("trip_photo.jpg"));
    }

    @Test
    @DisplayName("inserts text in the middle of the file name")
    void insertsInTheMiddle() throws Exception {

        write("photo.jpg", "img");

        int errors = new FileNameInserterProcess(
            5, "_01", tempDir.toFile(), false, true, false, false, null).doProcess();

        assertEquals(0, errors);
        assertEquals("img", read("photo_01.jpg"));
    }

    @Test
    @DisplayName("does nothing when the inserted text is empty")
    void doesNothingWhenInsertedTextIsEmpty() throws Exception {

        write("photo.jpg", "img");

        int errors = new FileNameInserterProcess(
            0, "", tempDir.toFile(), false, true, false, false, null).doProcess();

        assertEquals(0, errors);
        assertEquals("img", read("photo.jpg"));
    }

    @Test
    @DisplayName("inserts text into directory names when enabled")
    void insertsIntoDirectoryNamesWhenEnabled() throws Exception {

        Path album = Files.createDirectory(tempDir.resolve("album"));

        int errors = new FileNameInserterProcess(
            0, "2024_", album.toFile(), false, false, true, false, null).doProcess();

        assertEquals(0, errors);
        assertFalse(Files.exists(tempDir.resolve("album")));
        assertTrue(Files.isDirectory(tempDir.resolve("2024_album")));
    }

    @Test
    @DisplayName("does not enter subdirectories when recurse is false")
    void doesNotEnterSubdirectoriesWhenRecurseIsFalse() throws Exception {

        Path nested = Files.createDirectory(tempDir.resolve("nested"));
        write(nested.resolve("photo.jpg"), "inner");
        write("photo.jpg", "root");

        int errors = new FileNameInserterProcess(
            0, "x_", tempDir.toFile(), false, true, false, false, null).doProcess();

        assertEquals(0, errors);
        assertEquals("root", read("x_photo.jpg"));
        assertTrue(Files.exists(nested.resolve("photo.jpg")));
    }

    @Test
    @DisplayName("only inserts into files that match the filter")
    void appliesFilterRegex() throws Exception {

        write("keep.jpg", "keep");
        write("skip.jpg", "skip");

        int errors = new FileNameInserterProcess(
            0, "x_", tempDir.toFile(), false, true, false, true, "keep").doProcess();

        assertEquals(0, errors);
        assertEquals("keep", read("x_keep.jpg"));
        assertTrue(Files.exists(tempDir.resolve("skip.jpg")));
    }

    @Test
    @DisplayName("reports an error when the root directory does not exist")
    void reportsErrorWhenRootDoesNotExist() {

        int errors = new FileNameInserterProcess(0, "x", tempDir.resolve("missing").toFile(), false).doProcess();

        assertEquals(1, errors);
    }

    private void write(String name, String content) throws IOException {

        write(tempDir.resolve(name), content);
    }

    private void write(Path path, String content) throws IOException {

        Files.writeString(path, content, StandardCharsets.UTF_8);
    }

    private String read(String name) throws IOException {

        return Files.readString(tempDir.resolve(name), StandardCharsets.UTF_8);
    }
}
