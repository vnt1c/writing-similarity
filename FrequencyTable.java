import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * The FrequencyTable class is used to store the FrequencyList objects
 * associated with every word found across all the passages provided.
 *
 * @author Ryan Hung
 *      E-mail: ryan.hung@stonybrook.edu
 *      Stony Brook ID: 116086123
 *      Recitation: R02
 */

public class FrequencyTable extends ArrayList<FrequencyList> {
    /**
     * Iterates through passages and constructs FrequencyLists with each
     * Passage’s appropriate word frequencies
     *
     * Postconditions:
     *     A new FrequencyTable object has been constructed, containing a
     *     Collection of FrequencyLists with information from all Passages
     *
     * @param passages - a collection containing the Passage objects to be
     *                 parsed through
     * @return The FrequencyTable constructed from each Passage in passages.
     */
    public static FrequencyTable buildTable(ArrayList<Passage> passages) {
        FrequencyTable fq = new FrequencyTable();
        Set<String> allWords = new HashSet<>();
        for (Passage p : passages) { allWords.addAll(p.getWords()); }

        for (String word : allWords) {
            FrequencyList fl = new FrequencyList(word, passages);
            fq.add(fl);
        }

        return fq;
    }

    /**
     * Adds a Passage into the FrequencyTable and updates the FrequencyLists
     * accordingly
     *
     * Preconditions:
     *      The Passage p is neither null nor empty.
     *
     * Postconditions:
     *      p’s values for each of its keys have been appropriately mapped
     *      into each FrequencyList in the table.
     *
     * @param p - the Passage being iterated over and integrated into the
     *          table.
     * @throws IllegalArgumentException - If the given Passage is null or empty.
     */
    public void addPassage(Passage p) throws IllegalArgumentException {
        if (p == null) throw new IllegalArgumentException("Passage cannot be " +
                "null.");
        for (FrequencyList fl : this) { fl.addPassage(p); }
    }

    /**
     * Returns the frequency of the given word in the given Passage.
     *
     * @param word - the word to search for
     * @param p - the passage to look in
     * @return the frequency of the given word in the given Passage
     */
    public int getFrequency(String word, Passage p) {
        return (int) (p.getWordFrequency(word) * 100);
    }
}