package deors.tools.filemanager.lowercaserenamer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("LowerCaseRenamerProcess")
public class LowerCaseRenamerProcessTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("leaves files that are already lower case unchanged")
    void leavesLowerCaseNamesUnchanged() throws Exception {

        write("already.txt", "ok");

        int errors = new LowerCaseRenamerProcess(
            tempDir.toFile(), false, true, false, false, null).doProcess();

        assertEquals(0, errors);
        assertEquals("ok", read("already.txt"));
    }

    @Test
    @DisplayName("renames mixed-case files on a case-sensitive file system")
    void renamesMixedCaseFiles() throws Exception {

        assumeTrue(isCaseSensitiveFileSystem(), "case-changing rename needs a case-sensitive file system");

        write("Photo.JPG", "img");

        int errors = new LowerCaseRenamerProcess(
            tempDir.toFile(), false, true, false, false, null).doProcess();

        assertEquals(0, errors);
        assertFalse(Files.exists(tempDir.resolve("Photo.JPG")));
        assertEquals("img", read("photo.jpg"));
    }

    @Test
    @DisplayName("renames mixed-case directories when enabled")
    void renamesMixedCaseDirectories() throws Exception {

        assumeTrue(isCaseSensitiveFileSystem(), "case-changing rename needs a case-sensitive file system");

        Files.createDirectory(tempDir.resolve("Album"));

        int errors = new LowerCaseRenamerProcess(
            tempDir.toFile(), true, false, true, false, null).doProcess();

        assertEquals(0, errors);
        assertFalse(Files.exists(tempDir.resolve("Album")));
        assertTrue(Files.isDirectory(tempDir.resolve("album")));
    }

    @Test
    @DisplayName("does not enter subdirectories when recurse is false")
    void doesNotEnterSubdirectoriesWhenRecurseIsFalse() throws Exception {

        assumeTrue(isCaseSensitiveFileSystem(), "case-changing rename needs a case-sensitive file system");

        Path nested = Files.createDirectory(tempDir.resolve("nested"));
        write(nested.resolve("Inner.TXT"), "inner");
        write("Root.TXT", "root");

        int errors = new LowerCaseRenamerProcess(
            tempDir.toFile(), false, true, false, false, null).doProcess();

        assertEquals(0, errors);
        assertEquals("root", read("root.txt"));
        assertTrue(Files.exists(nested.resolve("Inner.TXT")));
    }

    @Test
    @DisplayName("only renames files that match the filter")
    void appliesFilterRegex() throws Exception {

        assumeTrue(isCaseSensitiveFileSystem(), "case-changing rename needs a case-sensitive file system");

        write("Keep.JPG", "keep");
        write("Skip.JPG", "skip");

        int errors = new LowerCaseRenamerProcess(
            tempDir.toFile(), false, true, false, true, "Keep").doProcess();

        assertEquals(0, errors);
        assertEquals("keep", read("keep.jpg"));
        assertTrue(Files.exists(tempDir.resolve("Skip.JPG")));
    }

    @Test
    @DisplayName("reports an error when the root directory does not exist")
    void reportsErrorWhenRootDoesNotExist() {

        int errors = new LowerCaseRenamerProcess(tempDir.resolve("missing").toFile(), false).doProcess();

        assertEquals(1, errors);
    }

    private boolean isCaseSensitiveFileSystem() throws IOException {

        Path probe = tempDir.resolve("CaseProbe.tmp");
        Files.writeString(probe, "x", StandardCharsets.UTF_8);
        return !Files.exists(tempDir.resolve("caseprobe.tmp"));
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
