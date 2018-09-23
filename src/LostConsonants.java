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
 * @version 1
 */
public class LostConsonants {
    /** List of String Arrays to hold the given dictionary. */
    private static ArrayList<String> dict;
    /** List of String Arrays to hold the results of LostConsonants. */
    private static ArrayList<String> results;
    /** boolean variable to account for removed full stops from given String. */
    private static boolean removedStop = false;

    /**
     * Main method for LostConsonants class.
     * @param args - [0] file path to dictionary, [1] word, phrase or sentence
     *             to manipulate
     */
    public static void main(String[] args) {
        //Check the number of args
        switch (args.length) {
            case 0: System.out.println("Expected 2 command line arguments, but "
                        + "got 0.\nPlease provide the path to the dictionary "
                        + "file as the first argument and a sentence as the "
                        + "second argument.");
                    System.exit(0);
            case 1: System.out.println("Expected 2 command line arguments, but "
                        + "got 1.\nPlease provide the path to the dictionary "
                        + "file as the first argument and a sentence as the "
                        + "second argument.");
                    System.exit(0);
            case 3: System.out.println("Expected 2 command line arguments, but "
                        + "got 3.\nPlease provide the path to the dictionary "
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

        //read in dictionary from command line
        dict = FileUtil.readLines(args[0]);
        results = new ArrayList<String>();

        String s = args[1];

        s = removeFullStop(s);

        loseConstonant(s);

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
     * count the constonants in phrase and remove them one at a time, checking
     * each new word to see if it valid.
     *
     * @param phrase - String to mutate
     */
    public static void loseConstonant(String phrase) {
        /*Define regex to find consonants (case-insensitive) and declare method
        variables */
        Pattern p = Pattern.compile("([b-df-hj-np-tv-z])",
                Pattern.CASE_INSENSITIVE);
        ArrayList<Integer> remove = new ArrayList<Integer>();
        int rI = 0;

        //Iterate through each word evaluating each character
        for (int i = 0; i < phrase.length(); i++) {
            String isCon = phrase.substring(i, i + 1);
            Matcher m = p.matcher(isCon);
            if (m.find()) {
                String newPhrase = phrase.substring(0, i)
                        + phrase.substring(i + 1);
                results.add(rI, newPhrase);
                rI++;
            }
        }

        //reset the rI counter
        rI = 0;

        //Validate mutated words against the given dictionary (dict)
        for (int i = 0; i < results.size(); i++) {
            String test = results.get(i);
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
                remove.add(rI, i);
                rI++;
            }
        }

        //Check if a full stop had been removed, if so then add it back in.
        if (removedStop) {
            for (int i = 0; i < results.size(); i++) {
                String temp = results.get(i) + ".";
                results.remove(i);
                results.add(i, temp);
            }
        }

        //Check if there are words to remove from the list
        if (!remove.isEmpty()) {
            int[] removeArray = listToArrInt(remove);
            removeArray = quicksort(removeArray, 0, removeArray.length - 1);

            for (int i = 0; i < removeArray.length; i++) {
                results.remove(removeArray[i]);
            }
        }
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
}