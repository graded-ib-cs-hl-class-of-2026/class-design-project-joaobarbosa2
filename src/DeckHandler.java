import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;

public class DeckHandler {
    private InterfaceHandler interfaceHandler = new InterfaceHandler();
    public static HashMap<String, Deck> decks = new HashMap<String, Deck>();

    IOHandler ioHandler = new IOHandler();

    /** 
     * Parser for the decks; Call on init of program to load all decks from save
     */
    public void parseDecks() {
        // file folder decks, find all decks and add them to the decks HashMap
        String[] files = ioHandler.getFilesInDirectory("decks");

        String deckNameCapitalized = "[!] No Deck Name";
        boolean shuffleSetting = false;
        int flashcardsStudied = 0;
        double seed = 0;

        for (String fileName : files) {
            String deckName = fileName.replace(".deck", "");

            ArrayList<Card> cards = new ArrayList<Card>();

            ioHandler.OpenFile("decks/" + fileName);

            int currentLine = 0;

            while (ioHandler.fileHasNextLine()) {
                currentLine++;

                if (currentLine == 1) {
                    deckNameCapitalized = ioHandler.getNextLine();
                }

                else if (currentLine == 2) {
                    String shuffleSettingString = ioHandler.getNextLine();
                    shuffleSetting = Boolean.parseBoolean(shuffleSettingString);
                }
                else if (currentLine == 3) {
                    seed = Double.parseDouble(ioHandler.getNextLine());
                }
                else if (currentLine == 4) {
                    flashcardsStudied = Integer.parseInt(ioHandler.getNextLine());
                } else {

                    String line = ioHandler.getNextLine();
                    if (line.equals("")) {
                        continue;
                    }
                    String[] parts = line.split("\\|\\|\\|");
                    if (parts.length != 2) {
                        System.out.println("Invalid line format: " + line);
                        continue;
                    }
                    String term = parts[0];
                    String definition = parts[1];
                    cards.add(new Card(term, definition));
                }
            }

            decks.put(deckName.toLowerCase(), new Deck(deckNameCapitalized, shuffleSetting, cards, flashcardsStudied, seed));
        }
    }

    /**
     * On init, creates the decks directory if it doesn't exist... just makes sure structure is what is expected
     */
    public void deckHandler() {
        File deckDir = new File("decks");
        if (!deckDir.exists()) {
            deckDir.mkdir();
        }
    }

    /**
     * Create a new deck and add it to the decks HashMap
     */
    public void createDeck() {
        String deckName = interfaceHandler.input("▶ Deck name ▶ ").strip();

        if (decks.containsKey(deckName)) {
            System.out.println("▶ Deck already exists.");
            return;
        }

        // Random order setting
        double seed = Math.random();
        int flashcardsStudied = 0;

        // add deck name & randomized setting to file
        ioHandler.writeFileLine("./decks/" + deckName.toLowerCase() + ".deck", deckName);
        ioHandler.writeFileLine("./decks/" + deckName.toLowerCase() + ".deck", "false");

        // Auto converts seed to string
        ioHandler.writeFileLine("./decks/" + deckName.toLowerCase() + ".deck", seed + "");

        // add randomized seed setting to file so playbacks are in the same order
        ioHandler.writeFileLine("./decks/" + deckName.toLowerCase() + ".deck", "0");

        decks.put(deckName.toLowerCase(), new Deck(deckName, false, new ArrayList<Card>(), flashcardsStudied, seed));
        System.out.println("Deck created ▶ " + deckName);
    }

    /**
     * View all decks in the decks HashMap
     */
    public void viewDecks() {
        if (decks.isEmpty()) {
            System.out.println("▶ No decks available.");
            return;
        }

        for (String deckNameKey : decks.keySet()) {

            String deckName = decks.get(deckNameKey).getName();

            // Below is logic for formatting the box
            String bar = "─";

            int deckNameLength = Math.max(deckName.length() - 2, 0);
            
            int deckCardsLength = decks.get(deckNameKey).getCards().size();
            boolean randomized = decks.get(deckNameKey).getRandomized();

            // Nicely format the deck name to look like a box
            // Unicode characters from https://superuser.com/questions/1544925/how-can-i-draw-nice-boxes-with-rounded-corners-using-unicode-characters
            System.out.print("\u001B[34m");
            System.out.println("╭──" + bar.repeat(deckNameLength) + "──╮");
            System.out.println("│ " + deckName + " │ │ " + deckCardsLength + " card" + (deckCardsLength > 1 ? "s" : "") + " │ " + (randomized ? "Randomized" : "Ordered"));
            System.out.println("╰──" + bar.repeat(deckNameLength) + "──╯");
            System.out.print("\u001B[0m");
        }
    }
}