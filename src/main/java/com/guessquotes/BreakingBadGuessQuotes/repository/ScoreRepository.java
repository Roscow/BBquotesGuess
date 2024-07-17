package com.guessquotes.BreakingBadGuessQuotes.repository;
import com.guessquotes.BreakingBadGuessQuotes.models.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findAllByOrderByScoreDesc();

}
