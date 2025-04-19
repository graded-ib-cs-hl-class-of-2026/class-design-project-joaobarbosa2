import java.util.Collections;
import java.util.ArrayList;

// For setting seed: // https://stackoverflow.com/questions/6284589/setting-a-seed-to-shuffle-arraylist-in-java-deterministically
import java.util.Random;

public class PracticeDeck {

    InterfaceHandler interfaceHandler = new InterfaceHandler();

    // Shuffle the deck and return a copy of it
    public ArrayList<Card> shuffleDeckCopy(Deck deck, double seed) {
        // Make a copy of list of cards in the deck
        // https://www.w3schools.com/java/java_arraylist.asp
        ArrayList<Card> deckCopy = new ArrayList<>(deck.getCards());

        // Set the seed for the random number generator
        // https://stackoverflow.com/questions/6284589/setting-a-seed-to-shuffle-arraylist-in-java-deterministically
        Collections.shuffle(deckCopy, new Random((int)(seed * 100000)));

        return deckCopy;
    }

    public void Practice(Deck deck) {
        interfaceHandler.clearConsole();
        System.out.println("--------------------------------");
        System.out.println("▶ Practicing with deck: " + deck.getName());
        System.out.println("▶ Type '/quit' to quit practicing.");

        System.out.println("╭─────────────────╮");
        System.out.println("│  \u001B[34m▲ Term\u001B[0m         │");
        System.out.println("│  \u001B[32m● Definition\u001B[0m   │");
        System.out.println("│  ✎ Press enter  │");
        System.out.println("╰─────────────────╯");

        boolean shuffle = deck.getRandomized();
        int flashcardsStudied = deck.getFlashcardsStudied();
        double seed = deck.getSeed();

        // check if already started practicing
        if (flashcardsStudied > 0) {
            System.out.println("▶ Already started practicing. Continuing from card " + (flashcardsStudied + 1) + ".");
        } else {
            System.out.println("▶ Starting from the beginning.");
        }

        ArrayList<Card> deckCopy;

        if (shuffle) {
            deckCopy = shuffleDeckCopy(deck, seed);
        } else {
            deckCopy = deck.getCards();
        }

        boolean quit = false;

        int counter = flashcardsStudied - 1;

        while (!quit) {
            counter++;
            // Check if the user has studied all the cards in the deck
            // deckCopy.size() from:
            // https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/Java-array-size-explained-by-example#:~:text=To%20get%20the%20size%20of%20a%20Java%20array%2C%20you%20use,the%20String's%20length()%20method.
            if (counter >= deckCopy.size()) {
                System.out.println("▶ No more cards in the deck. Exiting practice mode.");
                deck.resetFlashcardsStudied();
                break;
            }

            // if (userInput.equals("q")) {
            //     quit = true;
            // } else {

            // Hashmap.get() and other functionalities from https://www.w3schools.com/java/java_ref_hashmap.asp
            Card currentCard = deckCopy.get(counter);

            int termLength = currentCard.getTerm().length() + 4;
            int definitionLength = currentCard.getDefinition().length() + 4;

            String bar = "─";

            deck.studyCard();

            // Print out nice looking box that is dynamic to the length of the term and definition
            System.out.print("\u001B[34m");
            System.out.println("╭" + bar.repeat(termLength) + "╮");
            System.out.println("│  " + currentCard.getTerm() + "  │");
            // String.repeat() method https://www.w3schools.com/java/java_ref_hashmap.asp
            String userInput = interfaceHandler.input("╰" + bar.repeat(termLength) + "╯");
            System.out.print("\u001B[0m");

            // Check if the user wants to quit
            if (userInput.equals("/quit")) {
                quit = true;
            }

            // Box for definition; same as term in terms of formatting / design
            System.out.print("\u001B[32m");
            System.out.println("╭" + bar.repeat(definitionLength) + "╮");
            System.out.println("│  " + currentCard.getDefinition() + "  │");
            userInput = interfaceHandler.input("╰" + bar.repeat(definitionLength) + "╯");
            System.out.print("\u001B[0m");

            // Check if the user wants to quit
            if (userInput.equals("/quit")) {
                quit = true;
            }
        }
        System.out.println("▶ Practice session ended.");
    }
}
