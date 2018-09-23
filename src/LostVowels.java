import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
    /** List of String Arrays to hold the results of LostVowels. */
    private static ArrayList<String> results;
    /** boolean variable to account for removed full stops from given String. */
    private static boolean removedStop = false;
    /** Case 0 for args checker. */
    private static final int ZERO = 0;
    /** Case 1 for args checker. */
    private static final int ONE = 1;
    /** Case 3 for args checker. */
    private static final int THREE = 3;

    /**
     * Main method for LostVowels class.
     * @param args - [0] file path to dictionary, [1] word, phrase or sentence
     *             to manipulate
     */
    public static void main(String[] args) {
        //Check the number of args
//        switch (args.length) {
//            case ZERO: System.out.println("Expected 2 command line arguments, but "
//                    + "got 0.\nPlease provide the path to the dictionary "
//                    + "file as the first argument and a sentence as the "
//                    + "second argument.");
//                System.exit(0);
//            case ONE: System.out.println("Expected 2 command line arguments, but "
//                    + "got 1.\nPlease provide the path to the dictionary "
//                    + "file as the first argument and a sentence as the "
//                    + "second argument.");
//                System.exit(0);
//            case THREE: System.out.println("Expected 2 command line arguments, but "
//                    + "got 3.\nPlease provide the path to the dictionary "
//                    + "file as the first argument and a sentence as the "
//                    + "second argument.");
//                System.exit(0);
//            default: break;
//        }

        //Check if filepath is good
//        String filepath = args[0];
//        File file = new File(filepath);
//        if (file.isDirectory() || !file.exists()) {
//            System.out.println("File not found: " + filepath
//                    + " (No such file or directory)\nInvalid dictionary, "
//                    + "aborting.");
//            System.exit(0);
//        }

//        //read in dictionary from command line
//        dict = FileUtil.readLines(args[0]);
//        results = new ArrayList<String>();
//
//        String s = args[1];
//
//        s = LostConsonants.removeFullStop(s);
//
//        loseVowels(s);
//
//        if (!results.isEmpty()) {
//            for (int i = 0; i < results.size(); i++) {
//                System.out.println(results.get(i));
//            }
//            System.out.println("Found " + results.size() + " alternatives.");
//        } else {
//            System.out.println("Could not find any alternatives.");
//        }
        //validate the arguments passed
        LostConsonants.argsCheck(args);

        //read in dictionary from command line
        dict = FileUtil.readLines(args[0]);
        results = new ArrayList<String>();

        //read in the String from command line
        String s = args[1];

        //prepare string for mutation
        s = LostConsonants.removeFullStop(s);

        //mutate and validate string
        results = loseVowels(s);

        //print the results
        if (!results.isEmpty()) {
            for (int i = 0; i < results.size(); i++) {
                System.out.println(results.get(i));
            }
            System.out.println("Found " + results.size() + " alternatives.");
        } else {
            System.out.println("Could not find any alternatives.");
        }
    }

    /**
     * Using regex (inspired by my Information Retrieval background), find and
     * count the vowels in phrase and remove them one at a time, checking
     * each new word to see if it valid.
     *
     * @param phrase - String to mutate
     * @return - Filled and Validated ArrayList of Mutated Strings
     */
    public static ArrayList<String> loseVowels(String phrase) {
        //Create the remove ArrayList of Integers
        ArrayList<Integer> remove;
        /*Remove the consonants from phrase one at a time and add results to
        holder ArrayList*/
        ArrayList<String> holder = new ArrayList<>();
        holder = LostConsonants.loseCharacter("([b-df-hj-np-tv-z])", phrase, holder);
        //Validate the results class variable
        remove = LostConsonants.dictCheck(holder, dict);
        //Replace the full stops
        holder = LostConsonants.replaceStop(holder);
        //Remove any invalid phrases from results based on the remove ArrayList
        holder = LostConsonants.removeInValid(remove, holder);
        return holder;

//        /*Define regex to find vowels (case-insensitive) and declare method
//        variables */
//        Pattern p = Pattern.compile("([aeiuo])",
//                Pattern.CASE_INSENSITIVE);
//        ArrayList<Integer> remove = new ArrayList<Integer>();
//        int rI = 0;
//
//        //Iterate through each word evaluating each character
//        for (int i = 0; i < phrase.length(); i++) {
//            String isCon = phrase.substring(i, i + 1);
//            Matcher m = p.matcher(isCon);
//            if (m.find()) {
//                String newPhrase = phrase.substring(0, i)
//                        + phrase.substring(i + 1);
//                results.add(rI, newPhrase);
//                rI++;
//            }
//        }
//
//        //reset the rI counter
//        rI = 0;
//
//        //Validate mutated words against the given dictionary (dict)
//        for (int i = 0; i < results.size(); i++) {
//            String test = results.get(i);
//            String[] check = test.split(" ");
//            boolean notValid = false;
//
//            for (int j = 0; j < check.length; j++) {
//                /* Idea to use this lambda expression instead of the
//                 * ArrayList.contains() method originated from the referenced
//                 * StackOverflow post. Improved it to be inline with Java 8
//                 * Standards by using the .anyMatch() instead of the
//                 * .filter().findFirst().isPresent() method stream.
//                 * https://stackoverflow.com/questions/8751455/arraylist-
//                 * contains-case-sensitivity
//                 */
//                int finalJ = j;
//                if (!dict.stream().anyMatch(s ->
//                        s.equalsIgnoreCase(check[finalJ]))
//                        && !check[finalJ].contains(",")
//                        && !check[finalJ].contains(".")) {
//                    notValid = true;
//                }
//            }
//
//            if (notValid) {
//                remove.add(rI, i);
//                rI++;
//            }
//        }
//
//        //Check if a full stop had been removed, if so then add it back in.
//        if (removedStop) {
//            for (int i = 0; i < results.size(); i++) {
//                String temp = results.get(i) + ".";
//                results.remove(i);
//                results.add(i, temp);
//            }
//        }
//
//        //Check if there are words to remove from the list
//        if (!remove.isEmpty()) {
//            int[] removeArray = LostConsonants.listToArrInt(remove);
//            removeArray = LostConsonants.quicksort(removeArray,
//                    0, removeArray.length - 1);
//
//            for (int i = 0; i < removeArray.length; i++) {
//                results.remove(removeArray[i]);
//            }
//        }
    }
}