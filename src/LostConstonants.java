import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

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
    public static ArrayList<String> dict;
    public static ArrayList<String> results;

    /**
     * Main method for LostConstonants class
     * @param args - [0] file path to dictionary, [1] word, phrase or sentance
     *             to manipulate
     */
    public static void main (String [] args) {
        //Check the number of args
        switch (args.length) {
            case 0: throw new IllegalArgumentException("Expected 2 command line"
                        + " arguments, but got 0.\n Please provide the path to "
                        + "the dictionary file as the first argument and a "
                        + "sentence as the second argument.");
            case 1: throw new IllegalArgumentException("Expected 2 command line"
                        + " arguments, but got 1.\n Please provide the path to "
                        + "the dictionary file as the first argument and a "
                        + "sentence as the second argument.");
            case 3: throw new IllegalArgumentException("Expected 2 command line"
                    + " arguments, but got 3.\n Please provide the path to "
                    + "the dictionary file as the first argument and a "
                    + "sentence as the second argument.");
            default: break;
        }

        //Check if filepath is good
        String filepath = args[0];
        File file = new File(filepath);
        if (file.isDirectory() || !file.exists()) {
            throw new IllegalArgumentException("File not found: " + filepath
                    + " (No such file or directory) Invalid dictionary, "
                    + "aborting.");

        }

        //read in dictionary from command line
        dict = FileUtil.readLines(args[0]);
        results = new ArrayList<String>();

        String s = args[1];

        loseConstonant(s);

        if (!results.isEmpty()) {
            for (int i = 0; i < results.size(); i++) {
                System.out.println(results.get(i));
            }
            System.out.println("Found " + results.size() + " alternatives");
        } else {
            System.out.println("Could not find any alternavites");
        }
    }

    /**
     * Using regex (inspired by my Information Retrieval background), find and
     * count the constonants in phrase and remove them one at a time, checking
     * each new word to see if it valid.
     *
     * @param phrase - String to mutate
     */
    public static void loseConstonant (String phrase) {
        Pattern p = Pattern.compile("([b-df-hj-np-tv-z])",
                Pattern.CASE_INSENSITIVE);
        ArrayList<Integer> remove = new ArrayList<Integer>();
        int rI = 0;

        //Iterate through each word evaluating each character
        for (int i = 0; i < phrase.length(); i++) {
            String isCon = phrase.substring(i, i+1);
            Matcher m = p.matcher(isCon);
            if (m.find()) {
                String newPhrase = phrase.substring(0, i) +
                        phrase.substring(i+1);
                results.add(rI, newPhrase);
                rI++;
            }
        }

        //reset the rI counter
        rI = 0;

        for (int i = 0; i < results.size(); i++) {
            String test = results.get(i);
            String[] check = test.split(" ");
            for (int j = 0; j < check.length; j++) {
                if (!dict.contains(check[j])) {
                    remove.add(rI, i);
                }
            }
        }

        int[] removeArray = listToArrInt(remove);
        removeArray = quicksort(removeArray, 0, removeArray.length - 1);


        for (int i = 0; i < removeArray.length; i++) {
            results.remove(removeArray[i]);
        }
    }

    /**
     * QuickSort method inspired by experience with Data Structures and
     * Algorithms module in 2nd Year of UG. Clarification on bug in partition
     * method from Stack Overflow post
     * (https://stackoverflow.com/questions/11196571/quick-sort-sorts-descending
     * -not-ascending)
     *
     * @param target
     * @param low
     * @param high
     * @return
     */
    public static int[] quicksort(int[] target, int low, int high) {
        int l = low;
        int h = high;
        int temp = 0;
        int middle = target[(low+high)/2];

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
     * Simple method to convert an List<Integer> object to an int[] array object
     * @param list - List to convert to array
     * @return
     */
    public static int[] listToArrInt(List<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i).intValue();
        }
        return array;
    }
}