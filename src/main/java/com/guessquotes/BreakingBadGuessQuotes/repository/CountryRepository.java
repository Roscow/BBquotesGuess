package com.guessquotes.BreakingBadGuessQuotes.repository;
import com.guessquotes.BreakingBadGuessQuotes.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
