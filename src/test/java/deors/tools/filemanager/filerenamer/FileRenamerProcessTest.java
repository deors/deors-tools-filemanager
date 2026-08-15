package deors.tools.filemanager.filerenamer;

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

@DisplayName("FileRenamerProcess")
public class FileRenamerProcessTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("replaces the matching part of the file name")
    void replacesMatchingText() throws Exception {

        write("vacation_draft.jpg", "img");

        int errors = new FileRenamerProcess(
            "_draft", "", tempDir.toFile(), false, true, false, false, null).doProcess();

        assertEquals(0, errors);
        assertFalse(Files.exists(tempDir.resolve("vacation_draft.jpg")));
        assertEquals("img", read("vacation.jpg"));
    }

    @Test
    @DisplayName("leaves files that do not match the regex unchanged")
    void leavesNonMatchingFilesUnchanged() throws Exception {

        write("notes.txt", "ok");

        int errors = new FileRenamerProcess(
            "_draft", "", tempDir.toFile(), false, true, false, false, null).doProcess();

        assertEquals(0, errors);
        assertEquals("ok", read("notes.txt"));
    }

    @Test
    @DisplayName("can replace capturing groups")
    void replacesCapturingGroups() throws Exception {

        write("first_second.txt", "ok");

        int errors = new FileRenamerProcess(
            "(\\w+)_(\\w+)", "$2-$1", tempDir.toFile(), false, true, false, false, null).doProcess();

        assertEquals(0, errors);
        assertEquals("ok", read("second-first.txt"));
    }

    @Test
    @DisplayName("renames directories when enabled")
    void renamesDirectoriesWhenEnabled() throws Exception {

        Files.createDirectory(tempDir.resolve("album_draft"));

        int errors = new FileRenamerProcess(
            "_draft", "", tempDir.toFile(), true, false, true, false, null).doProcess();

        assertEquals(0, errors);
        assertFalse(Files.exists(tempDir.resolve("album_draft")));
        assertTrue(Files.isDirectory(tempDir.resolve("album")));
    }

    @Test
    @DisplayName("does not enter subdirectories when recurse is false")
    void doesNotEnterSubdirectoriesWhenRecurseIsFalse() throws Exception {

        Path nested = Files.createDirectory(tempDir.resolve("nested"));
        write(nested.resolve("photo_draft.jpg"), "inner");
        write("photo_draft.jpg", "root");

        int errors = new FileRenamerProcess(
            "_draft", "", tempDir.toFile(), false, true, false, false, null).doProcess();

        assertEquals(0, errors);
        assertEquals("root", read("photo.jpg"));
        assertTrue(Files.exists(nested.resolve("photo_draft.jpg")));
    }

    @Test
    @DisplayName("only renames files that match the filter")
    void appliesFilterRegex() throws Exception {

        write("keep_draft.jpg", "keep");
        write("skip_draft.jpg", "skip");

        int errors = new FileRenamerProcess(
            "_draft", "", tempDir.toFile(), false, true, false, true, "keep").doProcess();

        assertEquals(0, errors);
        assertEquals("keep", read("keep.jpg"));
        assertTrue(Files.exists(tempDir.resolve("skip_draft.jpg")));
    }

    @Test
    @DisplayName("reports an error when the root directory does not exist")
    void reportsErrorWhenRootDoesNotExist() {

        int errors = new FileRenamerProcess("a", "b", tempDir.resolve("missing").toFile(), false).doProcess();

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
