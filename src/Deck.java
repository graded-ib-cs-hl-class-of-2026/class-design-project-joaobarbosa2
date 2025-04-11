import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    private String name;
    private boolean randomized = false;
    private int flashcardsStudied = 0;
    private double seed = 0;
    private IOHandler ioHandler = new IOHandler();

    /**
     * Constructor for the Deck class when creating a new deck from scratch.
     * @param name
     */
    public Deck(String name) {
        this.cards = new ArrayList<Card>();
        this.name = name;
        this.randomized = false;
        this.seed = Math.random();
    }

    /**
     * Overloaded constructor for creating a deck w/ the cards predefined
     * (ie when loading from save)
     * @param name
     * @param randomized
     * @param cards
     */
    public Deck(String name, boolean randomized, ArrayList<Card> cards, int flashcardsStudied, double seed) {
        this.cards = cards;
        this.name = name;
        this.randomized = randomized;
        this.flashcardsStudied = flashcardsStudied;
        this.seed = seed;
    }

    public String getName() {
        return name;
    }

    // Allow other classes to see if this deck is randomized or not
    public boolean getRandomized() {
        return randomized;
    }

    public int getFlashcardsStudied() {
        return flashcardsStudied;
    }

    public void studyCard() {
        flashcardsStudied++;
        ioHandler.editFileLine("decks/" + name + ".deck", 4, flashcardsStudied + "");
    }

    public void resetFlashcardsStudied() {
        this.seed = Math.random();
        IOHandler ioHandler = new IOHandler();
        ioHandler.editFileLine("decks/" + name + ".deck", 3, this.seed + "");
        flashcardsStudied = 0;
        ioHandler.editFileLine("decks/" + name + ".deck", 4, flashcardsStudied + "");
    }

    public double getSeed() {
        return seed;
    }

    /**
     * Add a card to this deck
     * @param term
     * @param definition
     */
    public void addCard(String term, String definition) {

        // checking if card save is malformatted 
        if (term.equals("") || definition.equals("")) {
            System.out.println("▶ Card term or definition cannot be empty.");
            return;
        }
        if (term.contains("|||") || definition.contains("|||")) {
            System.out.println("▶ Card term or definition cannot contain '|||'.");
            return;
        }

        // check if card already exists, loops through cards to check if term already exists
        // it's already lowercase so no need to do it again
        for (Card card : cards) {
            if (card.getTerm().equals(term)) {
                System.out.println("▶ Card already exists.");
                return;
            }
        }

        Card card = new Card(term, definition);

        // Add the card to the deck file
        ioHandler.writeFileLine("decks/" + name + ".deck", term + "|||" + definition);

        cards.add(card);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    // Display all the cards in the deck
    public void displayCards() {
        // Needed refresher on how to use the for-each loop
        // https://www.w3schools.com/java/java_foreach_loop.asp
        for (Card card : cards) {
            System.out.println("Term: " + card.getTerm() + ", Definition: " + card.getDefinition());
        }
    }

    // Interface for adding new cards functionality -> Prompts users for term and definition until /quit
    public void addCardsFunction() {
        InterfaceHandler interfaceHandler = new InterfaceHandler();

        System.out.println("▶ Adding Cards");

        // Loop to add cards until user types /quit
        while (true) {
            System.out.println("▶ Type /quit to stop adding cards");
            String term = interfaceHandler.input("▶ Term ▶ ");
            if (term.equals("/quit")) {
                return;
            }

            String definition = interfaceHandler.input("▶ Definition ▶ ");
            if (definition.equals("/quit")) {
                return;
            }
            
            addCard(term, definition);
        }
    }


}
