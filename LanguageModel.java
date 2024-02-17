import java.util.HashMap;
import java.util.Random;

public class LanguageModel {

    // The map of this model.
    // Maps windows to lists of charachter data objects.
    HashMap<String, List> CharDataMap;
    
    // The window length used in this model.
    int windowLength;
    
    // The random number generator used by this model. 
	private Random randomGenerator;

    /** Constructs a language model with the given window length and a given
     *  seed value. Generating texts from this model multiple times with the 
     *  same seed value will produce the same random texts. Good for debugging. */
    public LanguageModel(int windowLength, int seed) {
        this.windowLength = windowLength;
        randomGenerator = new Random(seed);
        CharDataMap = new HashMap<String, List>();
    }

    /** Constructs a language model with the given window length.
     * Generating texts from this model multiple times will produce
     * different random texts. Good for production. */
    public LanguageModel(int windowLength) {
        this.windowLength = windowLength;
        randomGenerator = new Random();
        CharDataMap = new HashMap<String, List>();
    }

    /** Builds a language model from the text in the given file (the corpus). */
	public void train(String fileName) {
		// Your code goes here
	}

    // Computes and sets the probabilities (p and cp fields) of all the
	// characters in the given list. */
	public void calculateProbabilities(List probs) {
        int totalChars = 0;	
        // Computes how many characters exist in total
        for (int i = 0; i < probs.getSize(); i++) {
            totalChars += probs.listIterator(i).current.cp.count;
        }
        // For each character in the given list, this loop computes and set it's probabilities (p and cp fields)
        for (int i = 0; i < probs.getSize(); i++) {
            ListIterator iter = probs.listIterator(i);
            CharData currentChar = iter.current.cp;
            CharData previousChar = (i == 0) ? null : probs.listIterator(i - 1).current.cp;
            currentChar.p = (double) currentChar.count / totalChars;
            if (i == 0) {
                currentChar.cp = currentChar.p;
            } else {
                currentChar.cp = currentChar.p + previousChar.cp;
            }
        }
	}

    // Returns a random character from the given probabilities list.
	public char getRandomChar(List probs) {
		double r = Math.random(); // Drawing a random number in [0,1)
        int i = 0;
        /* Iterates the list, reading the cumulative probabilities
        (the cp fields) as we go along.*/
        while ((i < probs.getSize()) && (probs.listIterator(i).current.cp.cp < r)) {
            i++;
        } 
        /* Stops at the element whose cumulative probability is greater than
        r, and returns the character of this element */
        return probs.get(i).chr;
	}

    /**
	 * Generates a random text, based on the probabilities that were learned during training. 
	 * @param initialText - text to start with. If initialText's last substring of size numberOfLetters
	 * doesn't appear as a key in Map, we generate no text and return only the initial text. 
	 * @param numberOfLetters - the size of text to generate
	 * @return the generated text
	 */
	public String generate(String initialText, int textLength) {
		// Your code goes here
	}

    /** Returns a string representing the map of this language model. */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (String key : CharDataMap.keySet()) {
			List keyProbs = CharDataMap.get(key);
			str.append(key + " : " + keyProbs + "\n");
		}
		return str.toString();
	}
}
