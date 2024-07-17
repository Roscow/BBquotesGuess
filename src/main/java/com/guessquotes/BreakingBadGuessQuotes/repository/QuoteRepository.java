package com.guessquotes.BreakingBadGuessQuotes.repository;
import com.guessquotes.BreakingBadGuessQuotes.models.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    @Query(value = "SELECT * FROM Quote ORDER BY RANDOM() LIMIT 20", nativeQuery = true)
    List<Quote> findRandomQuotes();
}
