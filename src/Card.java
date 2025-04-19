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

    /**
     * Returns the term of the card
     */
    public String getTerm() {
        return this.term;
    }

    /**
     * Returns the definition of the card
     */
    public String getDefinition() {
        return this.definition;
    }

    /**
     * Sets the term of the card
     * @param term
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * Sets the definition of the card
     * @param definition
     */
    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
