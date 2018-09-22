import java.util.ArrayList;

/**
 * Class that will remove any constants from a phrase that is passed in from the
 * command arguments, check the new word against a given dictionary of words
 * (also passed in from the command line) and if that word is valid, it will be
 * printed to the command line.
 *
 * @author 180009917
 * @dateCreated 22/09/2018
 * @version 1
 * @dateEdited 22/09/2018
 */
public class LostConstonants {

    /**
     * Main method for LostConstonants class
     * @param args - [0] file path to dictionary, [1] word, phrase or sentance
     *             to manipulate
     */
    public static void main (String [] args) {
        //read in dictionary from command line
        ArrayList<String> lines = FileUtil.readLines(args[0]);
        String s = args[1];

        System.out.println(s);

        for(int i = 0; i < 5; i++) {
            System.out.println(lines.get(i));
        }
    }
}