package com.guessquotes.BreakingBadGuessQuotes.models;
import jakarta.persistence.*;


@Entity
public class TriviaResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quote_id")
    private Quote quote;

    @ManyToOne
    @JoinColumn(name = "character_id")
    private BBCharacter selectedCharacter;

    private String sessionId;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public BBCharacter getSelectedCharacter() {
        return selectedCharacter;
    }

    public void setSelectedCharacter(BBCharacter selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
