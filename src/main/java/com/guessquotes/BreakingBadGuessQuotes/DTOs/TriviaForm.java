package com.guessquotes.BreakingBadGuessQuotes.DTOs;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class TriviaForm {
    @NotNull
    private List<@NotNull Answer> answers;

    // Getters y Setters

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public static class Answer {
        @NotNull
        private Long quoteId;

        @NotNull
        private Long characterId;

        // Getters y Setters

        public Long getQuoteId() {
            return quoteId;
        }

        public void setQuoteId(Long quoteId) {
            this.quoteId = quoteId;
        }

        public Long getCharacterId() {
            return characterId;
        }

        public void setCharacterId(Long characterId) {
            this.characterId = characterId;
        }
    }
}
