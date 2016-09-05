import com.compactsolutions.CsvSplitter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CsvSplitterTest {

    private final static String inputFileString = "test\\resources\\reference-input.csv";
    private final static String outputDirectoryString = "test\\resources";
    private final static int numberOfOutputFiles = 3;

    @Test
    public final void csvSplitterShouldSplitOrdersEvenlyAcrossOutputFiles() throws IOException {
        final CsvSplitter csvSplitter = new CsvSplitter(inputFileString, outputDirectoryString, numberOfOutputFiles);
        csvSplitter.splitOrders();

        String actualOutputFile0 = readFileAsString("0.csv");
        String actualOutputFile1 = readFileAsString("1.csv");
        String actualOutputFile2 = readFileAsString("2.csv");

        String referenceOutputFile0 = readFileAsString(outputDirectoryString, "reference-0.csv");
        String referenceOutputFile1 = readFileAsString(outputDirectoryString, "reference-1.csv");
        String referenceOutputFile2 = readFileAsString(outputDirectoryString, "reference-2.csv");

        assertEquals(actualOutputFile0, referenceOutputFile0);
        assertEquals(actualOutputFile1, referenceOutputFile1);
        assertEquals(actualOutputFile2, referenceOutputFile2);
    }

    @AfterClass
    public static void deleteRemainingFiles() throws IOException {
        Files.deleteIfExists(Paths.get(outputDirectoryString, "0.csv"));
        Files.deleteIfExists(Paths.get(outputDirectoryString, "1.csv"));
        Files.deleteIfExists(Paths.get(outputDirectoryString, "2.csv"));
    }

    private static String readFileAsString(String fileString) throws IOException {
        Path file = Paths.get(fileString);
        return new String(Files.readAllBytes(file));
    }
}
