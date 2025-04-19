public class Main {
    public void start() {

        // IOHandler ioHandler = new IOHandler();
        InterfaceHandler interfaceHandler = new InterfaceHandler();
        DeckHandler deckHandler = new DeckHandler();

        // load in decks from storage
        deckHandler.parseDecks();

        interfaceHandler.navigateCommand();
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.start();
    }
}
