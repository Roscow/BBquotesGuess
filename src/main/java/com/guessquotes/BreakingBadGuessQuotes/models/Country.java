package com.guessquotes.BreakingBadGuessQuotes.models;
import jakarta.persistence.*;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String flagCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlagCode() {
        return flagCode;
    }

    public void setFlagCode(String flagCode) {
        this.flagCode = flagCode;
    }
}
