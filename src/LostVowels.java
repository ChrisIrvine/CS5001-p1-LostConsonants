//import statements
import java.util.ArrayList;

/**
 * Class that will remove any vowels from a phrase that is passed in from the
 * command arguments, check the new word against a given dictionary of words
 * (also passed in from the command line) and if that word is valid, it will be
 * printed to the command line.
 *
 * @author 180009917
 * @version 1
 */
public class LostVowels {
    /** List of String Arrays to hold the given dictionary. */
    private static ArrayList<String> dict;

    /**
     * Main method for LostVowels class.
     *
     * @param args - [0] file path to dictionary, [1] word, phrase or sentence
     *             to manipulate
     */
    public static void main(String[] args) {
        ArrayList<String> results;
        //validate the arguments passed
        LostConsonants.argsCheck(args);

        //read in dictionary from command line
        dict = FileUtil.readLines(args[0]);

        //read in the String from command line
        String s = args[1];

        //prepare string for mutation
        s = LostConsonants.removeFullStop(s);

        //mutate and validate string
        results = loseVowels(s);

        //print the results
        LostConsonants.printResults(results);
    }

    /**
     * Using regex (inspired by my Information Retrieval background), find and
     * count the vowels in phrase and remove them one at a time, checking
     * each new word to see if it valid.
     *
     * @param phrase - String to mutate
     * @return - Filled and Validated ArrayList of Mutated Strings
     */
    private static ArrayList<String> loseVowels(String phrase) {
        //Create the remove ArrayList of Integers
        ArrayList<Integer> remove;

        /*Remove the consonants from phrase one at a time and add results to
        holder ArrayList*/
        ArrayList<String> holder = new ArrayList<>();
        holder = LostConsonants.loseCharacter("([aeiou])",
                phrase, holder);

        //Validate the results class variable
        remove = LostConsonants.dictCheck(holder, dict);

        //Replace the full stops
        holder = LostConsonants.replaceStop(holder);

        //Remove any invalid phrases from results based on the remove ArrayList
        holder = LostConsonants.removeInValid(remove, holder);

        //Return the results
        return holder;
    }
}