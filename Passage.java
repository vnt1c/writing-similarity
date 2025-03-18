import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * The Passage class is used to store the words of a passage and their
 * corresponding frequencies within a specific text.
 *
 * @author Ryan Hung
 *      E-mail: ryan.hung@stonybrook.edu
 *      Stony Brook ID: 116086123
 *      Recitation: R02
 */

public class Passage extends HashMap<String, Integer> {
    // Variables
    private String title;
    private int wordCount;
    private HashMap<String, Double> similarTitles;
    private Set<String> stopWords;

    /**
     * This is a constructor which sets the given title and file equal to the
     * current Passage's associated variables.
     *
     * @param title - title for this object
     * @param file - file for this object
     */
    public Passage(String title, File file) {
        this.title = title;
        wordCount = 0;
        similarTitles = new HashMap<>();
        createStopWords();
        parseFile(file);
    }

    /**
     * Initializes and sets the stopWords HashSet equal to all the stop words
     * listed in StopWords.txt.
     */
    private void createStopWords() {
        stopWords = new HashSet<>();

        // Add each line of the txt file to the HashSet
        try (BufferedReader reader = new BufferedReader(new FileReader(
                "StopWords.txt"))) {
            String line = reader.readLine();

            while (line != null) {
                stopWords.add(line);
                line = reader.readLine();
            }
        } catch (Exception e) {}
    }

    /**
     * Reads the given text file and counts each word’s occurrence,
     * excluding stop words, and inserts them into the table
     *
     * @param file - the file to examine
     */
    public void parseFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();

            while (line != null) {
                if (line.isEmpty()) {
                    line = reader.readLine();
                    continue;
                }
                // Clean up the line after reading it
                line = line.toLowerCase().replaceAll("[^a-z\\s]", "");
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (!stopWords.contains(word)) {
                        this.put(word, this.getOrDefault(word, 0) + 1);
                        wordCount++;
                    }
                }
                line = reader.readLine();
            }
        } catch (Exception e) {}
    }

    /**
     * Calculates the similarity between two Passage objects using the
     * formula above and modifies their similarTitles accordingly
     *
     * @param passage1 - the first passage to compare
     * @param passage2 - the second passage to compare
     * @return a double representing the cosine similarity of the two passages
     */
    public static double cosineSimilarity(Passage passage1,
                                            Passage passage2) {
        // cos(alpha) = a / (sqrt(b) * sqrt(c))
        double dotProduct = 0;
        double magnitude1 = 0;
        double magnitude2 = 0;
        double ans = 0;

        // Dot product & magnitude of 1
        for (String word : passage1.getWords()) {
            dotProduct += passage1.getWordFrequency(word) * passage2.getWordFrequency(word);
            magnitude1 += Math.pow(passage1.getWordFrequency(word), 2);
        }

        // Magnitude of 2
        for (String word : passage2.getWords()) {
            magnitude2 += Math.pow(passage2.getWordFrequency(word), 2);
        }

        if (magnitude1 == 0 || magnitude2 == 0) return 0.0;

        ans = dotProduct / (Math.sqrt(magnitude1) * Math.sqrt(magnitude2));
        passage1.similarTitles.put(passage2.getTitle(), ans);
        passage2.similarTitles.put(passage1.getTitle(), ans);

        return ans;
    }

    /**
     * returns the relative frequency of the given word in this Passage
     * (occurrences/wordCount)
     *
     * @param word - the word to search for
     * @return the relative frequency of the given word within the passage
     */
    public double getWordFrequency(String word) {
        return (double) this.getOrDefault(word, 0) / wordCount;
    }

    /**
     * returns a Set containing all of the words in this Passage
     *
     * @return a Set containing all of the words in this Passage
     */
    public Set<String> getWords() {
        return this.keySet();
    }

    /**
     * Returns a neatly formatted string containing the titles this passage
     * is similar to
     *
     * @return a neatly formatted string with similar titles
     */
    @Override
    public String toString() {
        List<Map.Entry<String, Double>> sortedEntries = new ArrayList<>(similarTitles.entrySet());
        sortedEntries.sort(Map.Entry.comparingByKey());
        String line1 = "";
        String line2 = "";

        for (int i = 0; i < sortedEntries.size(); i++) {
            String title = sortedEntries.get(i).getKey();
            int similarity = (int) Math.round(sortedEntries.get(i).getValue() * 100);
            if (i < 2) line1 += title + "(" + similarity + "%),";
            else {
                line2 += title + "(" + similarity + "%)";
                if (i == 2) line2 += ", ";
            }
        }

        return String.format("%-25s%-1s%-54s%n%-25s%-1s%-54s", title, "| ", line1, "", "| ", line2);
    }

    /**
     * Returns the title of this passage
     *
     * @return the title of this passage
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the word count of this passage
     *
     * @return the word count of this passage
     */
    public int getWordCount() {
        return wordCount;
    }

    /**
     * Returns the similarTitles of this passage
     *
     * @return the similarTitles of this passage
     */
    public HashMap<String, Double> getSimilarTitles() {
        return similarTitles;
    }

    /**
     * Sets title to the given parameter
     *
     * @param title - the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets word count to the given parameter
     *
     * @param wordCount - the new word count
     */
    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    /**
     * Sets similarTitles to the given parameter
     *
     * @param similarTitles - the new similarTitles
     */
    public void setSimilarTitles(HashMap<String, Double> similarTitles) {
        this.similarTitles = similarTitles;
    }
}