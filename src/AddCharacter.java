import java.io.File;
import java.util.ArrayList;

/**
 * Class to add characters to a given string, evaluate the mutated string and
 * then print the validated string.
 *
 * @author 180009917
 * @version 1
 */
public class AddCharacter {
    /** Dictionary to evaluate the new phrases against. */
    private static ArrayList<String> dict;
    /** Zero (0) value for argsCheck Switch Statement. */
    private static final int ZERO = 0;
    /** One (1) value for argsCheck Switch Statement. */
    private static final int ONE = 1;
    /** Four (4) value for argsCheck Switch Statement. */
    private static final int FOUR = 4;
    /** Integer to inform program which set of characters to add. */
    private static int whichChar;

    /**
     * Main method for AddCharacter class. It will validate the arguments passed
     * from the command line, extract their values, prepare the phrase for
     * mutation, mutate then validate the phrase and print the validated
     * results.
     * @param args - [0] = filepath to dictionary of words, [1] = word, phrase
     *             or sentence to mutate, [2] = integer declaring which
     *             character set to use
     */
    public static void main(String[] args) {
        ArrayList<String> results;
        //Validate the arguments passed
        argsCheck(args);

        //read in dictionary from command line
        dict = FileUtil.readLines(args[0]);

        //read in the String from command line
        String s = args[1];

        /*Determine if user wants consonants (default), vowel (1)
         or alphabet (0).*/
        if (args.length == 2) {
            whichChar = 2;
        } else if (Integer.parseInt(args[2]) == 0) {
            whichChar = 1;
        } else {
            whichChar = 0;
        }

        //prepare string for mutation
        s = LostConsonants.removeFullStop(s);

        //mutate and validate string
        results = addCharacter(s);

        //print the results
        LostConsonants.printResults(results);
    }

    /**
     * Method to check the number of arguments passed into the program and to
     * then validate the path to the dictionary file (checks to see if the file
     * is a .txt file).
     *
     * @param args - array of arguments passed from the command line.
     */
    private static void argsCheck(String[] args) {
        //Check if the number of arguments is good
        switch (args.length) {
            case ZERO: System.out.println("Expected 2 or 3 command line "
                    + "arguments, but got 0.\nPlease provide the path to the "
                    + "dictionary file as the first argument and a sentence as "
                    + "the second argument, with an int as the third argument "
                    + "(0 for vowels, 1 for the entire alphabet and leave blank"
                    + " for consonants).");
                System.exit(0);
            case ONE: System.out.println("Expected 2 or 3 command line "
                    + "arguments, but got 1.\nPlease provide the path to the "
                    + "dictionary file as the first argument and a sentence as "
                    + "the second argument, with an int as the third argument "
                    + "(0 for vowels, 1 for the entire alphabet and leave blank"
                    + " for consonants).");
                System.exit(0);
            case FOUR: System.out.println("Expected 2 or 3 command line "
                    + "arguments, but got 4.\nPlease provide the path to the "
                    + "dictionary file as the first argument and a sentence as "
                    + "the second argument, with an int as the third argument "
                    + "(0 for vowels, 1 for the entire alphabet and leave blank"
                    + " for consonants).");
                System.exit(0);
            default: break;
        }

        //Check if filepath is good
        String filepath = args[0];
        File file = new File(filepath);
        if (file.isDirectory() || !file.exists()) {
            System.out.println("File not found: " + filepath
                    + " (No such file or directory)\nInvalid dictionary, "
                    + "aborting.");
            System.exit(0);
        }
    }

    /**
     * Method to execute series of methods to add a character to a given phrase
     * and then validate the new strings against the given dictionary.
     *
     * @param phrase - String to mutate and then validate.
     * @return - ArrayList of Strings containing the validated mutated phrases.
     */
    private static ArrayList<String> addCharacter(String phrase) {
        //Declare Method Variables
        ArrayList<String> holder = new ArrayList<>();
        ArrayList<Integer> remove;

        //Add characters to the given phrase
        holder = addCharacter(whichChar, phrase, holder);

        //Validate new phrases against the given dictionary
        remove = LostConsonants.dictCheck(holder, dict);

        //If removed a full stop from string, replace it
        holder = LostConsonants.replaceStop(holder);

        //Remove any invalid phrases from the results
        holder = LostConsonants.removeInValid(remove, holder);

        return holder;
    }

    /**
     * Method to add a character from a given set to a given phrase and then add
     * the new phrases to an ArrayList<String> to be returned.
     *
     * @param charCode - character set to add; 0 == vowels, 1 == alphabet,
     *                 default is consonants.
     * @param phrase - phrase to mutate and validate
     * @param results - ArrayList of valid mutated phrases.
     * @return - ArrayList of valid mutated phrases.
     */
    private static ArrayList<String> addCharacter(int charCode, String phrase,
                                                 ArrayList<String> results) {
        //Delcare class variables
        char[] addChar;
        ArrayList<Character> phraseChars = new ArrayList<>();

        //Decide which set of characters to add (consonants, vowels or all)
        if (charCode == 0) {
            addChar = "aeiou".toCharArray();
        } else if (charCode == 1) {
            addChar = "abcdefghijklomnopqrstuvwxyz".toCharArray();
        } else {
            addChar = "bcdfghjklmnpqrstvwxyz".toCharArray();
        }

        /*Separate the phrase to mutate into a Character array, then add each
        character to a ArrayList<Character>*/
        for (char c: phrase.toCharArray()) {
            phraseChars.add(c);
        }

        /*For each possible character to add to the phrase, then for each
        character in the original phrase, add a new character and store the
        newPhrase into an ArrayList<String>, then remove the added char*/
        for (char c: addChar) {
            for (int i = 0; i < phraseChars.size() + 1; i++) {
                phraseChars.add(i, c);
                StringBuilder newPhrase = new StringBuilder();

                for (char p : phraseChars) {
                    newPhrase.append(p);
                }

                results.add(newPhrase.toString());

                phraseChars.remove(i);
            }
        }

        return results;
    }
}