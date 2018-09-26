import java.io.File;
import java.util.ArrayList;

public class AddCharacter {
    //private static ArrayList<String> results;
    private static ArrayList<String> dict;
    //private static boolean removedStop = false;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int FOUR = 4;
    private static int whichChar;

    public static void main(String[] args) {
        ArrayList<String> results;
        //Validate the arguments passed
        argsCheck(args);

        //read in dictionary from command line
        dict = FileUtil.readLines(args[0]);

        //read in the String from command line
        String s = args[1];

        //Consonant (0) or Vowel (1)
        whichChar = Integer.getInteger(args[2]);

        //prepare string for mutation
        s = LostConsonants.removeFullStop(s);

        //mutate and validate string
        results = addConsonant(s);

        LostConsonants.printResults(results);
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

    public static void argsCheck(String[] args) {
        //Check if the number of arguments is good
        switch (args.length) {
            case ZERO: System.out.println("Expected 3 command line arguments"
                    + ", but got 0.\nPlease provide the path to the dictionary "
                    + "file as the first argument and a sentence as the "
                    + "second argument.");
                System.exit(0);
            case ONE: System.out.println("Expected 3 command line arguments"
                    + ", but got 1.\nPlease provide the path to the dictionary "
                    + "file as the first argument and a sentence as the "
                    + "second argument.");
                System.exit(0);
            case FOUR: System.out.println("Expected 3 command line arguments"
                    + ", but got 4.\nPlease provide the path to the dictionary "
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

    public static ArrayList<String> addConsonant(String phrase) {
        ArrayList<String> holder = new ArrayList<>();
        ArrayList<Integer> remove;

        holder = addCharacter(whichChar, phrase, holder);

        remove = LostConsonants.dictCheck(holder, dict);

        holder = LostConsonants.replaceStop(holder);

        holder = LostConsonants.removeInValid(remove, holder);

        return holder;
    }

    //get string into arraylist of characters
    public static ArrayList<String> addCharacter(int charCode, String phrase, ArrayList<String> results){
        char[] addChar;
        //char[] phraseChars = phrase.toCharArray();
        //char[] bigArray = new char[phraseChars.length+1];
        if (charCode == 0) {
            addChar= "bcdfghjklmnpqrstvwxyz".toCharArray();
        } else {
            addChar = "aeiou".toCharArray();
        }

        ArrayList<Character> phraseChars = new ArrayList<>();

        for (char c: phrase.toCharArray()) {
            phraseChars.add(c);
        }

        for (char c: addChar) {
            //System.arraycopy(phraseChars, 0, phraseChars, 1, bigArray.length);
            for (int i = 0; i < phraseChars.size(); i++) {
                phraseChars.add(i, c);

                results.add(phraseChars.toString());
            }
        }

        return results;
    }
}