package com.guessquotes.BreakingBadGuessQuotes.repository;
import com.guessquotes.BreakingBadGuessQuotes.models.BBCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BBCharacterRepository extends JpaRepository<BBCharacter, Long> {
}
