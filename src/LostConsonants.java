import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Class that will remove any constants from a phrase that is passed in from the
 * command arguments, check the new word against a given dictionary of words
 * (also passed in from the command line) and if that word is valid, it will be
 * printed to the command line.
 *
 * @author 180009917
 * @version 2 (devolved many in method functions into seperate methods)
 */
public class LostConsonants {
    /** List of String Arrays to hold the given dictionary. */
    private static ArrayList<String> dict;
    /** boolean variable to account for removed full stops from given String. */
    private static boolean removedStop = false;
    /** Case 0 for args checker. */
    private static final int ZERO = 0;
    /** Case 1 for args checker. */
    private static final int ONE = 1;
    /** Case 3 for args checker. */
    private static final int THREE = 3;

    /**
     * Main method for LostConsonants class.
     * @param args - [0] file path to dictionary, [1] word, phrase or sentence
     *             to manipulate
     */
    public static void main(String[] args) {
        ArrayList<String> results;
        //validate the arguments passed
        argsCheck(args);

        //read in dictionary from command line
        dict = FileUtil.readLines(args[0]);

        //read in the String from command line
        String s = args[1];

        //prepare string for mutation
        s = removeFullStop(s);

        //mutate and validate string
        results = loseConsonant(s);

        printResults(results);
        //print the results
//        if (!results.isEmpty()) {
//            for (int i = 0; i < results.size(); i++) {
//                System.out.println(results.get(i));
//            }
//            System.out.println("Found " + results.size() + " alternatives.");
//        } else {
//            System.out.println("Could not find any alternatives.");
//        }
    }

    /**
     * Simple method to assess the number of arguments passed into the Java
     * program. Then it will check if the filepath passed is good.
     * @param args - arguments passed from the command line
     */
    public static void argsCheck(String[] args) {
        //Check if the number of arguments is good
        switch (args.length) {
            case ZERO: System.out.println("Expected 2 command line arguments"
                    + ", but got 0.\nPlease provide the path to the dictionary "
                    + "file as the first argument and a sentence as the "
                    + "second argument.");
                System.exit(0);
            case ONE: System.out.println("Expected 2 command line arguments"
                    + ", but got 1.\nPlease provide the path to the dictionary "
                    + "file as the first argument and a sentence as the "
                    + "second argument.");
                System.exit(0);
            case THREE: System.out.println("Expected 2 command line arguments"
                    + ", but got 3.\nPlease provide the path to the dictionary "
                    + "file as the first argument and a sentence as the "
                    + "second argument.");
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
     * Using regex (inspired by my Information Retrieval background), find and
     * count the consonants in phrase and remove them one at a time, checking
     * each new word to see if it valid.
     *
     * @param phrase - String to mutate
     * @return - Filled and Valid ArrayList of Mutated Strings
     */
    public static ArrayList<String> loseConsonant(String phrase) {
        //Create the remove ArrayList of Integers
        ArrayList<Integer> remove;

        /*Remove the consonants from phrase one at a time and add results to
        holder ArrayList*/
        ArrayList<String> holder = new ArrayList<>();
        holder = loseCharacter("([b-df-hj-np-tv-z])", phrase, holder);

        //Validate the results class variable
        remove = dictCheck(holder, dict);

        //Replace the full stops
        holder = replaceStop(holder);

        //Remove any invalid phrases from results based on the remove ArrayList
        holder = removeInValid(remove, holder);

        //Return the results
        return holder;
    }

    /**
     * Method to remove characters based on a given regex on a given phrase and
     * add resulting new phrases to the results class variable.
     *
     * @param regex - regex string to find characters
     * @param phrase - phrase to mutate and store results in results
     * @param holder - ArrayList to add results to
     * @return - filled ArrayList of Strings
     */
    public static ArrayList<String> loseCharacter(String regex, String phrase,
                                                  ArrayList<String> holder) {
        Pattern p = Pattern.compile(regex,
                Pattern.CASE_INSENSITIVE);
        ArrayList<Integer> remove = new ArrayList<Integer>();
        int rI = 0;

        //Iterate through each word evaluating each character
        for (int i = 0; i < phrase.length(); i++) {
            String isCon = phrase.substring(i, i + 1);
            Matcher m = p.matcher(isCon);
            /*If target character has been found, remove it and add mutation
              to the results ArrayList*/
            if (m.find()) {
                String newPhrase = phrase.substring(0, i)
                        + phrase.substring(i + 1);
                holder.add(rI, newPhrase);
                rI++;
            }
        }
        return holder;
    }

    /**
     * Method to check an ArrayList of Strings (class variable results) against
     * a given dictionary (class variable dict). Any phrases that contain an
     * invalid word have their indexes stored within an ArrayList of Integers
     * (remove) which is returned
     *
     * @param holder - ArrayList of Strings holding current results
     * @param dict - ArrayList of Strings to validate mutated words against
     * @return - ArrayList of Integers containing indexes to remove from results
     */
    public static ArrayList<Integer> dictCheck(ArrayList<String> holder,
                                               ArrayList<String> dict) {
        ArrayList<Integer> remove = new ArrayList<>();
        int removeIndex = 0;

        for (int i = 0; i < holder.size(); i++) {
            String test = holder.get(i);
            String[] check = test.split(" ");
            boolean notValid = false;

            for (int j = 0; j < check.length; j++) {
                /* Idea to use this lambda expression instead of the
                 * ArrayList.contains() method originated from the referenced
                 * StackOverflow post. Improved it to be inline with Java 8
                 * Standards by using the .anyMatch() instead of the
                 * .filter().findFirst().isPresent() method stream.
                 * https://stackoverflow.com/questions/8751455/arraylist-
                 * contains-case-sensitivity
                 */
                int finalJ = j;
                if (!dict.stream().anyMatch(s ->
                        s.equalsIgnoreCase(check[finalJ]))
                        && !check[finalJ].contains(",")
                        && !check[finalJ].contains(".")) {
                    notValid = true;
                }
            }
            if (notValid) {
                remove.add(removeIndex, i);
                removeIndex++;
            }
        }

        return remove;
    }

    /**
     * Simple method to check if a full stop has been removed from the original
     * String, and if it has then replace it (assumes String is a sentence
     * therefore will replace the full stop at the end of the String).
     *
     * @param holder - ArrayList of Strings holding current results
     * @return - mutated ArrayList of Strings
     */
    public static ArrayList<String> replaceStop(ArrayList<String> holder) {
        //Check if a full stop had been removed, if so then add it back in.
        if (removedStop) {
            for (int i = 0; i < holder.size(); i++) {
                String temp = holder.get(i) + ".";
                holder.remove(i);
                holder.add(i, temp);
            }
        }
        return holder;
    }

    /**
     * Method to search through the results ArrayList for any invalid phrases
     * and remove them. In order to do this the remove ArrayList is converted to
     * an int Array and then placed into descending order using the quicksort
     * algorithm.
     *
     * @param removeList - ArrayList of Integers containing the indexes of
     *                   invalid phrases to remove to results.
     * @param holder - ArrayList of Strings holding current results
     * @return - mutated ArrayList of Strings
     */
    public static ArrayList<String> removeInValid(ArrayList<Integer> removeList,
                                     ArrayList<String> holder) {
        if (!removeList.isEmpty()) {
            int[] removeArray = listToArrInt(removeList);
            removeArray = quicksort(removeArray, 0, removeArray.length - 1);

            for (int i = 0; i < removeArray.length; i++) {
                holder.remove(removeArray[i]);
            }
        }
        return holder;
    }

    /**
     * QuickSort method inspired by experience with Data Structures and
     * Algorithms module in 2nd Year of UG.
     *
     * @param target - the array of integers to quicksort
     * @param low - the lowest index of the target array
     * @param high - the highest index of the target array
     * @return - sorted array (in descending order)
     */
    public static int[] quicksort(int[] target, int low, int high) {
        int l = low;
        int h = high;
        int temp = 0;
        int middle = target[(low + high) / 2];

        while (l < h) {
            while (target[l] > middle) {
                l++;
            }
            while (target[h] < middle) {
                h--;
            }
            if (h >= l) {
                temp = target[l];
                target[l] = target[h];
                target[h] = temp;
                l++;
                h--;
            }
        }

        if (low < h) {
            quicksort(target, low, h);
        }
        if (l < high) {
            quicksort(target, l, high);
        }

        return target;
    }

    /**
     * Simple method to convert an List<Integer> object to an int[] array
     * object.
     *
     * @param list - List to convert to array
     * @return - returns an array of integers
     */
    public static int[] listToArrInt(List<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i).intValue();
        }
        return array;
    }

    /**
     * Simple method to remove a full stop from a String. Method will also check
     * the position of the full stop in the String and react accordingly.
     *
     * @param s - String to remove the full stop from
     * @return - String with any full stops removed from it.
     */
    public static String removeFullStop(String s) {
        if (s.contains(".") && s.indexOf(".") != s.length() - 1) {
            s = s.substring(0, s.indexOf("."))
                    + s.substring(s.indexOf(".") + 1);
            removedStop = true;
        } else if (s.contains(".")) {
            s = s.substring(0, s.indexOf("."));
            removedStop = true;
        }

        return s;
    }

    public static void printResults(ArrayList<String> results) {
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
}