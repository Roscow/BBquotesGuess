package com.guessquotes.BreakingBadGuessQuotes.repository;
import com.guessquotes.BreakingBadGuessQuotes.models.QuoteSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteSuggestionRepository extends JpaRepository<QuoteSuggestion, Long> {
}
