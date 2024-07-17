package com.guessquotes.BreakingBadGuessQuotes.repository;
import com.guessquotes.BreakingBadGuessQuotes.models.TriviaResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TriviaResultRepository extends JpaRepository<TriviaResult, Long> {
    List<TriviaResult> findBySessionId(String sessionId);
}
