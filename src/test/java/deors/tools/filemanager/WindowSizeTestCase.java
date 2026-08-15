package deors.tools.filemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import deors.core.sensible.SensibleToolkit;
import deors.tools.filemanager.datechanger.DateChangerFrame;
import deors.tools.filemanager.dateshifter.DateShifterFrame;
import deors.tools.filemanager.filenameinserter.FileNameInserterFrame;
import deors.tools.filemanager.filenameresequencer.FileNameReSequencerFrame;
import deors.tools.filemanager.filenamesequencer.FileNameSequencerFrame;
import deors.tools.filemanager.filerenamer.FileRenamerFrame;
import deors.tools.filemanager.jpegmetadatasorter.JpegMetadataSorterFrame;
import deors.tools.filemanager.lowercaserenamer.LowerCaseRenamerFrame;
import deors.tools.filemanager.putzerointimeinator.PutZeroInTimeInatorFrame;
import deors.tools.filemanager.timezoneshifter.TimeZoneShifterFrame;

@DisplayName("Frame client area matches the designed content size")
public class WindowSizeTestCase {

    private static final String LOOK_AND_FEEL =
        "org.pushingpixels.substance.api.skin.SubstanceCremeCoffeeLookAndFeel";

    public WindowSizeTestCase() {

        super();
    }

    @BeforeAll
    static void installLookAndFeel() throws Exception {

        assumeFalse(GraphicsEnvironment.isHeadless(), "frame size checks need a display");

        SwingUtilities.invokeAndWait(() -> {
            if (!SensibleToolkit.setLookAndFeel(LOOK_AND_FEEL)) {
                SensibleToolkit.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        });
    }

    static Stream<Arguments> frames() {

        return Stream.of(
            Arguments.of("FileManagerFrame", (Supplier<JFrame>) FileManagerFrame::new, 445, 322),
            Arguments.of("DateChangerFrame", (Supplier<JFrame>) DateChangerFrame::new, 795, 230),
            Arguments.of("DateShifterFrame", (Supplier<JFrame>) DateShifterFrame::new, 795, 230),
            Arguments.of("TimeZoneShifterFrame", (Supplier<JFrame>) TimeZoneShifterFrame::new, 795, 230),
            Arguments.of("FileNameSequencerFrame", (Supplier<JFrame>) FileNameSequencerFrame::new, 795, 230),
            Arguments.of("FileNameReSequencerFrame", (Supplier<JFrame>) FileNameReSequencerFrame::new, 795, 230),
            Arguments.of("LowerCaseRenamerFrame", (Supplier<JFrame>) LowerCaseRenamerFrame::new, 795, 200),
            Arguments.of("PutZeroInTimeInatorFrame", (Supplier<JFrame>) PutZeroInTimeInatorFrame::new, 795, 200),
            Arguments.of("FileNameInserterFrame", (Supplier<JFrame>) FileNameInserterFrame::new, 795, 260),
            Arguments.of("FileRenamerFrame", (Supplier<JFrame>) FileRenamerFrame::new, 795, 260),
            Arguments.of("JpegMetadataSorterFrame", (Supplier<JFrame>) JpegMetadataSorterFrame::new, 795, 320));
    }

    @ParameterizedTest(name = "{0} client {2}x{3}")
    @MethodSource("frames")
    void contentPaneHasDesignedSize(String name, Supplier<JFrame> factory,
            int expectedWidth, int expectedHeight) throws Exception {

        SwingUtilities.invokeAndWait(() -> {
            JFrame frame = factory.get();
            try {
                Dimension client = frame.getContentPane().getSize();
                assertEquals(expectedWidth, client.width, () -> name + " client width");
                assertEquals(expectedHeight, client.height, () -> name + " client height");
            } finally {
                frame.dispose();
            }
        });
    }
}
