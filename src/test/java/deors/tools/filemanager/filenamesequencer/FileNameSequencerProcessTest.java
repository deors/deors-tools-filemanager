package deors.tools.filemanager.filenamesequencer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

@DisplayName("FileNameSequencerProcess")
public class FileNameSequencerProcessTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("renames a file using prefix, offset and padding")
    void sequencesASingleFile() throws Exception {

        write("original.jpg", "img");

        int errors = new FileNameSequencerProcess("img", 0, 3, tempDir.toFile(), false).doProcess();

        assertEquals(0, errors);
        assertFalse(Files.exists(tempDir.resolve("original.jpg")));
        assertEquals("img", read("img001.jpg"));
    }

    @Test
    @DisplayName("starts counting at offset plus one")
    void startsAtOffsetPlusOne() throws Exception {

        write("original.jpg", "img");

        int errors = new FileNameSequencerProcess("pic", 10, 2, tempDir.toFile(), false).doProcess();

        assertEquals(0, errors);
        assertEquals("img", read("pic11.jpg"));
    }

    @Test
    @DisplayName("does not pad when padding is zero")
    void doesNotPadWhenPaddingIsZero() throws Exception {

        write("original.jpg", "img");

        int errors = new FileNameSequencerProcess("img", 0, 0, tempDir.toFile(), false).doProcess();

        assertEquals(0, errors);
        assertEquals("img", read("img1.jpg"));
    }

    @Test
    @DisplayName("keeps the original extension")
    void keepsTheOriginalExtension() throws Exception {

        write("notes.txt", "txt");

        int errors = new FileNameSequencerProcess("doc", 0, 2, tempDir.toFile(), false).doProcess();

        assertEquals(0, errors);
        assertEquals("txt", read("doc01.txt"));
    }

    @Test
    @DisplayName("assigns a unique sequence number to each file")
    void assignsAUniqueSequenceToEachFile() throws Exception {

        write("one.jpg", "a");
        write("two.jpg", "b");

        int errors = new FileNameSequencerProcess("img", 0, 3, tempDir.toFile(), false).doProcess();

        assertEquals(0, errors);
        assertEquals(Set.of("img001.jpg", "img002.jpg"), fileNames());
        assertEquals(Set.of("a", "b"), Set.of(read("img001.jpg"), read("img002.jpg")));
    }

    @Test
    @DisplayName("skips a file that already has the target name")
    void skipsWhenNameDoesNotChange() throws Exception {

        write("img001.jpg", "img");

        int errors = new FileNameSequencerProcess("img", 0, 3, tempDir.toFile(), false).doProcess();

        assertEquals(0, errors);
        assertEquals("img", read("img001.jpg"));
    }

    @Test
    @DisplayName("does not enter subdirectories when recurse is false")
    void doesNotEnterSubdirectoriesWhenRecurseIsFalse() throws Exception {

        Path nested = Files.createDirectory(tempDir.resolve("nested"));
        write(nested.resolve("inner.jpg"), "inner");
        write("root.jpg", "root");

        int errors = new FileNameSequencerProcess("img", 0, 3, tempDir.toFile(), false).doProcess();

        assertEquals(0, errors);
        assertEquals("root", read("img001.jpg"));
        assertTrue(Files.exists(nested.resolve("inner.jpg")));
    }

    @Test
    @DisplayName("sequences files in subdirectories when recurse is true")
    void sequencesFilesWhenRecursing() throws Exception {

        Path nested = Files.createDirectory(tempDir.resolve("nested"));
        write(nested.resolve("inner.jpg"), "inner");

        int errors = new FileNameSequencerProcess("img", 0, 3, tempDir.toFile(), true).doProcess();

        assertEquals(0, errors);
        assertEquals("inner", Files.readString(nested.resolve("img001.jpg"), StandardCharsets.UTF_8));
    }

    @Test
    @DisplayName("only sequences files that match the filter")
    void appliesFilterRegex() throws Exception {

        write("keep.jpg", "keep");
        write("skip.txt", "skip");

        int errors = new FileNameSequencerProcess(
            "img", 0, 3, tempDir.toFile(), false, true, "\\.jpg$").doProcess();

        assertEquals(0, errors);
        assertEquals("keep", read("img001.jpg"));
        assertTrue(Files.exists(tempDir.resolve("skip.txt")));
    }

    @Test
    @DisplayName("reports an error when the root directory does not exist")
    void reportsErrorWhenRootDoesNotExist() {

        int errors = new FileNameSequencerProcess(
            "img", 0, 3, tempDir.resolve("missing").toFile(), false).doProcess();

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

    private Set<String> fileNames() throws IOException {

        Set<String> names = new HashSet<String>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(tempDir)) {
            for (Path path : stream) {
                if (Files.isRegularFile(path)) {
                    names.add(path.getFileName().toString());
                }
            }
        }
        return names;
    }
}
