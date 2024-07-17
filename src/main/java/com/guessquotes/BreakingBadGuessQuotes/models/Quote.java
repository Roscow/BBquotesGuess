package com.guessquotes.BreakingBadGuessQuotes.models;
import jakarta.persistence.*;

@Entity
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "character_id")
    private BBCharacter character;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(length = 1000, nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BBCharacter getCharacter() {
        return character;
    }

    public void setCharacter(BBCharacter character) {
        this.character = character;
    }
}
