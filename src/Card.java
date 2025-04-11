public class Card {
    private String term;
    private String definition;

    // Constructor for the Card class... very straightforward
    public Card(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }

    /*
     * Getters and Setters below
    */

    public String getTerm() {
        return this.term;
    }

    public String getDefinition() {
        return this.definition;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
