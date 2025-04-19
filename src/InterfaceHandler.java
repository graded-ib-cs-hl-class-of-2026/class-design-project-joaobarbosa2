import java.util.Scanner;

public class InterfaceHandler {
    private static Scanner scanner;

    /**
     * Constructor for the InterfaceHandler class; assigns sets the same
     * scanner for all instances as optimization
     */
    public InterfaceHandler() {
        scanner = new Scanner(System.in);
    }

    /**
     * Prompt the user for input; abstracts away the scanner
     * @param prompt
     * @return
     */
    public String input(String prompt) {
        System.out.print(prompt);
        String userInput = scanner.nextLine();
        return userInput;
    }

    public void clearConsole() {
        // https://stackoverflow.com/questions/10241217/how-to-clear-console-in-java
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void menu() {
        // Menu for options... also straightforward
        System.out.println("[create] Create deck");
        System.out.println("[add] Add flashcards to a deck");
        System.out.println("[cards] Pick a deck to see its cards");
        System.out.println("[view] View decks");
        System.out.println("[file] Open save file");
        System.out.println("[practice] Practice flashcards");
        System.out.println("[exit] Exit");
    }

    public void asciiArt() {
        // ASCII art from https://www.asciiart.eu/text-to-ascii-art
        System.out.println(
                "|     \n" +
                        "|     \n" +
                        "|     \n" +
                        "|      ,gggggggggggggg                                                \n" +
                        "|     dP\"\"\"\"\"\"88\"\"\"\"\"\" ,dPYb,                       ,dPYb,\n" +
                        "|     Yb,_    88       IP'`Yb                       IP'`Yb            \n" +
                        "|      `\"\"    88       I8  8I                       I8  8I          \n" +
                        "|          ggg88gggg   I8  8'                       I8  8'            \n" +
                        "|             88   8   I8 dP    ,gggg,gg    ,g,     I8 dPgg,          \n" +
                        "|             88       I8dP    dP\"  \"Y8I   ,8'8,    I8dP\" \"8I     \n" +
                        "|       gg,   88       I8P    i8'    ,8I  ,8'  Yb   I8P    I8         \n" +
                        "|        \"Yb,,8P      ,d8b,_ ,d8,   ,d8b,,8'_   8) ,d8     I8,       \n" +
                        "|          \"Y8P'      8P'\"Y88P\"Y8888P\"`Y8P' \"YY8P8P88P     `Y8   \n" +
                        "|                                                                     \n" +
                        "|                                                                     \n" +
                        "|      ,ggg, ,ggg,_,ggg,                                              \n" +
                        "|     dP\"\"Y8dP\"\"Y88P\"\"Y8b                                       \n" +
                        "|     Yb, `88'  `88'  `88                                             \n" +
                        "|      `\"  88    88    88                             gg              \n" +
                        "|          88    88    88                             \"\"              \n" +
                        "|          88    88    88    ,gggg,gg   ,ggg,,ggg,    gg     ,gggg,gg \n" +
                        "|          88    88    88   dP\"  \"Y8I  ,8\" \"8P\" \"8,   88    dP\"  \"Y8I \n" +
                        "|          88    88    88  i8'    ,8I  I8   8I   8I   88   i8'    ,8I \n" +
                        "|          88    88    Y8,,d8,   ,d8b,,dP   8I   Yb,_,88,_,d8,   ,d8b,\n" +
                        "|          88    88    `Y8P\"Y8888P\"`Y88P'   8I   `Y88P\"\"Y8P\"Y8888P\"`Y8\n" +
                        "|                                                                     \n" +
                        "|                                                                     \n" +
                        "|                                                                     ");
    }

    /** 
     * Initial command loop for the program; handles all commands and program functionalities
     */
    public void navigateCommand() {
        asciiArt();

        DeckHandler deckHandler = new DeckHandler();

        while (true) {
            menu();

            String command = input("Command ▶ ");

            clearConsole();

            System.out.println("\n--------------------------------\n");

            String deckName;
            Deck deck;

            // Routes the commands to each handler
            switch (command.toLowerCase()) {
                case "create":
                    // Tell user what they are doing / feedback for command
                    System.out.println("▶ Create deck");

                    // Start the create deck interface & process
                    deckHandler.createDeck();

                    break;

                case "add":
                    System.out.println("▶ Add flashcards to a deck");

                    // Showcase all existing decks in nice UI
                    deckHandler.viewDecks();

                    // Strip and lowercase the input to compare to the deck names from keys
                    deckName = input("▶ Target Deck ▶ ").strip().toLowerCase();

                    // Make sure deck exists
                    if (!DeckHandler.decks.containsKey(deckName)) {
                        System.out.println("▶ Deck does not exist.");
                        break;
                    }

                    // Retrieve the deck if found
                    deck = DeckHandler.decks.get(deckName);

                    // Add cards to the deck; start interface / functionality
                    deck.addCardsFunction();

                    break;
                case "view":
                    System.out.println("Viewing decks:");

                    // Showcase all existing decks in nice UI
                    deckHandler.viewDecks();

                    break;
                case "cards":

                    System.out.println("▶ Pick a deck to see its cards");

                    if (DeckHandler.decks.isEmpty()) {
                        System.out.println("▶ No decks available.");
                        break;
                    } else {

                        deckHandler.viewDecks();

                        deckName = input("▶ Target Deck ▶ ").strip().toLowerCase();

                        deck = DeckHandler.decks.get(deckName);

                        // Check if the deck exists
                        if (deck != null) {
                            deck.displayCards();
                        } else {
                            System.out.println("▶ Deck does not exist.");
                        }
                    }

                    break;
                case "file":
                    System.out.println("▶ Open save file");

                    // open the save folder on the person's computer
                    // get current directory
                    // https://stackoverflow.com/questions/4871051/how-to-get-the-current-working-directory-in-java
                    String currentDirectory = System.getProperty("user.dir");
                    // run open command in terminal (if on Mac), if not, run the command for Windows
                    // https://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java
                    String os = System.getProperty("os.name").toLowerCase();
                    // if Mac
                    if (os.contains("mac")) {
                        try {
                            // Running commands on terminal from:
                            // https://stackoverflow.com/questions/15356405/how-to-run-a-command-at-terminal-from-java-program
                            // exec function is deprecated, but works for now
                            Runtime.getRuntime().exec("open " + currentDirectory + "/decks");
                        } catch (Exception e) {
                            System.out.println("▶ Error opening save folder.");
                        }
                    // If windows
                    } else if (os.contains("win")) {
                        try {
                            Runtime.getRuntime().exec("explorer " + currentDirectory + "\\save");
                        } catch (Exception e) {
                            System.out.println("▶ Error opening save folder.");
                        }
                    } else {
                    // else just don't do anything since not familiar w/ OS (Linux, etc.)
                        System.out.println("▶ Unsupported OS. Please open the save folder manually.");
                    }

                    break;
                case "practice":
                    System.out.println("▶ Practice flashcards");

                    PracticeDeck practiceDeck = new PracticeDeck();

                    deckHandler.viewDecks();
                    deckName = input("▶ Target Deck ▶ ").strip().toLowerCase();
                    deck = DeckHandler.decks.get(deckName);
                    if (deck == null) {
                        System.out.println("▶ Deck does not exist.");
                        break;
                    }
                    practiceDeck.Practice(deck);

                    break;
                case "exit":
                    System.out.println("▶ Thank you for using the program!");

                    return;
                default:
                    System.out.println("▶ Invalid command");
                    break;
            }

            System.out.println("\n--------------------------------\n");
        }
    }

    // public void close() {
    //     scanner.close();
    // }
}
