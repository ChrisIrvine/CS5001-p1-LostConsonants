import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Simple file utility class for reading text files.
 *
 * @author ozgurakgun
 */
public class FileUtil {

    /**
     * Attempts to read the text file specified by filename and returns an array of Strings (lines) found in the file.
     *
     * @param filename of the file to read
     * @return Returns an array of Strings, each string representing a line of text from the file. If an exception occurs an empty array is returned.
     */
    public static ArrayList<String> readLines(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            ArrayList<String> lines = new ArrayList<String>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O Ooops: " + e.getMessage());
        }

        // If an exception occurred we will get to here as the return statement above was not executed
        // so setup a paragraphs array to return which contains the empty string
        return new ArrayList<>();
    }
}