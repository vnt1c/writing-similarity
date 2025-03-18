import java.io.File;
import java.util.*;

/**
 * The TextAnalyzer class is used to take input from the user and output
 * texts that may originate from the same author based on a tolerance level
 * provided by the user.
 *
 * @author Ryan Hung
 *      E-mail: ryan.hung@stonybrook.edu
 *      Stony Brook ID: 116086123
 *      Recitation: R02
 */

public class TextAnalyzer {
    public static void main(String[] args) {
        // Variables
        Scanner input = new Scanner(System.in);
        FrequencyTable fq; // Frequency table with all words/frequencies
        File[] directory; // File folder
        ArrayList<Passage> passages = new ArrayList<>(); // Passage list for fq
        String similarTitles = "";

        System.out.print("Enter a similarity percentage: ");
        double similarity = input.nextDouble();
        input.nextLine();
        System.out.print("Enter the directory of a folder of text files: ");
        String folder = input.nextLine();

        System.out.print("\nReading texts. . .\n\n");

        // Initializing directory array
        directory = new File(folder).listFiles(file -> !file.getName().equals(
                "StopWords.txt"));
        Arrays.sort(directory);

        // Array -> ArrayList for FrequencyTable
        for (File file : directory) { passages.add(new Passage(file.getName()
                .substring(0, file.getName().length() - 4), file)); }
        fq = FrequencyTable.buildTable(passages);

        int index = 1;
        for (int i = 0; i < passages.size(); i++) {
            for (int j = index; j < passages.size(); j++) {
                if (Passage.cosineSimilarity(passages.get(i),
                        passages.get(j)) > similarity) {
                    similarTitles += "'" + passages.get(i).getTitle() + "' " +
                            "and '" + passages.get(j).getTitle() + "'may have" +
                            " the same author (" + Math.round(Passage
                            .cosineSimilarity(passages.get(i), passages.get(j))
                            * 100) + "% " + "similar).\n";
                }
            }
            index++;
        }

        System.out.printf("%-25s%-1s%-54s%n", "Text (title)", "| ",
                "Similarities (%)");
        for (Passage a : passages) {
            System.out.println("-------------------------------------------" +
                    "-------------------------------------");
            System.out.println(a.toString());
        }

        System.out.print("\nSuspected Texts With Same Authors\n--------------" +
                "----------------------------------------------------------" +
                "--------\n" + similarTitles);
    }
}