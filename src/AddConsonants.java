import java.util.ArrayList;

public class AddConsonants {
    private static ArrayList<String> results;
    private static ArrayList<String> dict;
    private static boolean removedStop = false;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int THREE = 3;

    public static void main(String[] args) {
        //Validate the arguments passed
        LostConsonants.argsCheck(args);

        //read in dictionary from command line
        dict = FileUtil.readLines(args[0]);
        results = new ArrayList<String>();

        //read in the String from command line
        String s = args[1];

        //prepare string for mutation
        s = LostConsonants.removeFullStop(s);

        //mutate and validate string
        results = addConsonant(s);

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

    public static ArrayList<String> addConsonant(String phrase) {
        ArrayList<String> holder = new ArrayList<>();



        return holder;
    }

}