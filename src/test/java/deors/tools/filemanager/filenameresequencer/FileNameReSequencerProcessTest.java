package deors.tools.filemanager.filenameresequencer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("FileNameReSequencerProcess")
public class FileNameReSequencerProcessTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("shifts every matching file when start is 0")
    void shiftsAllFilesWhenStartIsZero() throws Exception {

        write("photo11.jpg", "a");
        write("photo12.jpg", "b");

        int errors = process("photo", 1, 0).doProcess();

        assertEquals(0, errors);
        assertFalse(Files.exists(tempDir.resolve("photo11.jpg")));
        assertEquals("a", read("photo12.jpg"));
        assertEquals("b", read("photo13.jpg"));
        assertNoTempFiles();
    }

    @Test
    @DisplayName("shifts consecutive files down using a two-step rename")
    void shiftsConsecutiveFilesDown() throws Exception {

        write("photo11.jpg", "a");
        write("photo12.jpg", "b");
        write("photo13.jpg", "c");

        int errors = process("photo", -1, 0).doProcess();

        assertEquals(0, errors);
        assertEquals("a", read("photo10.jpg"));
        assertEquals("b", read("photo11.jpg"));
        assertEquals("c", read("photo12.jpg"));
        assertFalse(Files.exists(tempDir.resolve("photo13.jpg")));
        assertNoTempFiles();
    }

    @Test
    @DisplayName("does not overwrite a file below the start index")
    void abortsWhenTargetIndexBelowStartExists() throws Exception {

        write("photo10.jpg", "keep");
        write("photo11.jpg", "a");
        write("photo12.jpg", "b");

        int errors = process("photo", -1, 11).doProcess();

        assertTrue(errors > 0);
        assertEquals("keep", read("photo10.jpg"));
        assertEquals("a", read("photo11.jpg"));
        assertEquals("b", read("photo12.jpg"));
        assertNoTempFiles();
    }

    @Test
    @DisplayName("applies a negative offset from start when the hole is free")
    void appliesNegativeOffsetWhenHoleIsFree() throws Exception {

        write("photo09.jpg", "keep");
        write("photo11.jpg", "a");
        write("photo12.jpg", "b");

        int errors = process("photo", -1, 11).doProcess();

        assertEquals(0, errors);
        assertEquals("keep", read("photo09.jpg"));
        assertEquals("a", read("photo10.jpg"));
        assertEquals("b", read("photo11.jpg"));
        assertFalse(Files.exists(tempDir.resolve("photo12.jpg")));
        assertNoTempFiles();
    }

    @Test
    @DisplayName("leaves files below start untouched on a positive offset")
    void leavesFilesBelowStartUntouched() throws Exception {

        write("photo10.jpg", "keep");
        write("photo11.jpg", "a");
        write("photo12.jpg", "b");

        int errors = process("photo", 1, 11).doProcess();

        assertEquals(0, errors);
        assertEquals("keep", read("photo10.jpg"));
        assertEquals("a", read("photo12.jpg"));
        assertEquals("b", read("photo13.jpg"));
        assertFalse(Files.exists(tempDir.resolve("photo11.jpg")));
        assertNoTempFiles();
    }

    @Test
    @DisplayName("aborts when the resulting index would be negative")
    void abortsWhenResultWouldBeNegative() throws Exception {

        write("photo0.jpg", "a");
        write("photo1.jpg", "b");

        int errors = process("photo", -1, 0).doProcess();

        assertTrue(errors > 0);
        assertEquals("a", read("photo0.jpg"));
        assertEquals("b", read("photo1.jpg"));
        assertNoTempFiles();
    }

    @Test
    @DisplayName("keeps the original numeric padding")
    void preservesOriginalPadding() throws Exception {

        write("photo0011.jpg", "a");
        write("photo0012.jpg", "b");

        int errors = process("photo", 1, 0).doProcess();

        assertEquals(0, errors);
        assertEquals("a", read("photo0012.jpg"));
        assertEquals("b", read("photo0013.jpg"));
        assertNoTempFiles();
    }

    @Test
    @DisplayName("grows the numeric token when padding is no longer enough")
    void growsTokenWhenPaddingOverflows() throws Exception {

        write("photo0099.jpg", "a");

        int errors = process("photo", 1, 0).doProcess();

        assertEquals(0, errors);
        assertEquals("a", read("photo0100.jpg"));
        assertFalse(Files.exists(tempDir.resolve("photo0099.jpg")));
        assertNoTempFiles();
    }

    @Test
    @DisplayName("only processes files that already have the given prefix")
    void filtersByPrefix() throws Exception {

        write("photo11.jpg", "a");
        write("other11.jpg", "keep");
        write("notes.txt", "notes");

        int errors = process("photo", 1, 0).doProcess();

        assertEquals(0, errors);
        assertEquals("a", read("photo12.jpg"));
        assertEquals("keep", read("other11.jpg"));
        assertEquals("notes", read("notes.txt"));
        assertNoTempFiles();
    }

    @Test
    @DisplayName("does nothing when the offset is zero")
    void doesNothingWhenOffsetIsZero() throws Exception {

        write("photo11.jpg", "a");

        int errors = process("photo", 0, 0).doProcess();

        assertEquals(0, errors);
        assertEquals("a", read("photo11.jpg"));
        assertNoTempFiles();
    }

    @Test
    @DisplayName("treats each directory as an independent sequence when recursing")
    void processesDirectoriesIndependentlyWhenRecursing() throws Exception {

        Path first = Files.createDirectory(tempDir.resolve("one"));
        Path second = Files.createDirectory(tempDir.resolve("two"));
        Files.writeString(first.resolve("photo11.jpg"), "a", StandardCharsets.UTF_8);
        Files.writeString(second.resolve("photo11.jpg"), "b", StandardCharsets.UTF_8);

        FileNameReSequencerProcess process = new FileNameReSequencerProcess(
            "photo", 1, 0, tempDir.toFile(), true);
        int errors = process.doProcess();

        assertEquals(0, errors);
        assertEquals("a", Files.readString(first.resolve("photo12.jpg"), StandardCharsets.UTF_8));
        assertEquals("b", Files.readString(second.resolve("photo12.jpg"), StandardCharsets.UTF_8));
        assertFalse(Files.exists(first.resolve("photo11.jpg")));
        assertFalse(Files.exists(second.resolve("photo11.jpg")));
    }

    @Test
    @DisplayName("does not enter subdirectories when recurse is false")
    void doesNotEnterSubdirectoriesWhenRecurseIsFalse() throws Exception {

        Path nested = Files.createDirectory(tempDir.resolve("nested"));
        write("photo11.jpg", "a");
        Files.writeString(nested.resolve("photo11.jpg"), "keep", StandardCharsets.UTF_8);

        int errors = process("photo", 1, 0).doProcess();

        assertEquals(0, errors);
        assertEquals("a", read("photo12.jpg"));
        assertEquals("keep", Files.readString(nested.resolve("photo11.jpg"), StandardCharsets.UTF_8));
    }

    @Test
    @DisplayName("aborts when two files would land on the same target name")
    void abortsWhenTwoFilesMapToTheSameTarget() throws Exception {

        write("photo099.jpg", "a");
        write("photo99.jpg", "b");

        int errors = process("photo", 1, 0).doProcess();

        assertTrue(errors > 0);
        assertEquals("a", read("photo099.jpg"));
        assertEquals("b", read("photo99.jpg"));
        assertNoTempFiles();
    }

    @Test
    @DisplayName("parses prefix, padded number and remaining suffix")
    void parsesSequencedNames() {

        FileNameReSequencerProcess process = process("IMG_", 0, 0);

        FileNameReSequencerProcess.SequencedName parsed = process.parseSequencedName("IMG_0010.jpg");
        assertEquals("IMG_0011.jpg", process.buildName(parsed, 11));

        assertNull(process.parseSequencedName("other0010.jpg"));
        assertNull(process.parseSequencedName("IMG_.jpg"));
        assertNull(process.parseSequencedName("IMG_abc.jpg"));
    }

    private FileNameReSequencerProcess process(String prefix, int offset, int start) {

        return new FileNameReSequencerProcess(prefix, offset, start, tempDir.toFile(), false);
    }

    private void write(String name, String content) throws IOException {

        Files.writeString(tempDir.resolve(name), content, StandardCharsets.UTF_8);
    }

    private String read(String name) throws IOException {

        return Files.readString(tempDir.resolve(name), StandardCharsets.UTF_8);
    }

    private void assertNoTempFiles() throws IOException {

        Set<String> leftovers = new HashSet<String>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(tempDir)) {
            for (Path path : stream) {
                if (path.getFileName().toString().contains(".reseqtmp")) {
                    leftovers.add(path.getFileName().toString());
                }
            }
        }
        assertTrue(leftovers.isEmpty(), () -> "temporary names left behind: " + leftovers);
    }
}
