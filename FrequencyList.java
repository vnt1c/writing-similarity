import java.util.ArrayList;
import java.util.HashMap;

/**
 * The FrequencyList class is used to store the frequencies of a particular
 * word within the passages provided.
 *
 * @author Ryan Hung
 *      E-mail: ryan.hung@stonybrook.edu
 *      Stony Brook ID: 116086123
 *      Recitation: R02
 */

public class FrequencyList {
    private final String word;
    private final ArrayList<Integer> frequencies;
    private final HashMap<String, Integer> passageIndices;

    /**
     * This constructor takes two parameters (word and passages) and
     * initializes all variables within this object.
     *
     * @param word - the word of this FrequencyList
     * @param passages - the ArrayList to add to this FrequencyList
     */
    public FrequencyList(String word, ArrayList<Passage> passages) {
        this.word = word;
        this.frequencies = new ArrayList<>();
        this.passageIndices = new HashMap<>();
        for (Passage p : passages) { addPassage(p); }
    }

    /**
     * Adds a Passage to this FrequencyList
     *
     * Postconditions:
     *      passageIndices now contains p’s title which maps to the next
     *      available index in this ArrayList
     *      The ArrayList now contains an additional index containing the
     *      frequency of “word” in the Passage
     *
     * @param p - the passage to add
     */
    public void addPassage(Passage p) {
        passageIndices.put(p.getTitle(), frequencies.size());
        frequencies.add((int) (p.getWordFrequency(word) * 100));
    }

    /**
     * Returns the frequency of “word” in the given Passage, 0 if the Passage
     * is not contained in this FrequencyList
     *
     * @param p - the passage to examine
     * @return the frequency of the word within said passage
     */
    public int getFrequency(Passage p) {
        return frequencies.get(passageIndices.get(p.getTitle()));
    }
}