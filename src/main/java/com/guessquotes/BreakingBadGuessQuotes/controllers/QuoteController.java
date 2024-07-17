package com.guessquotes.BreakingBadGuessQuotes.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.guessquotes.BreakingBadGuessQuotes.models.Quote;
import com.guessquotes.BreakingBadGuessQuotes.repository.QuoteRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    @Autowired
    private QuoteRepository quoteRepository;

    @GetMapping
    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quote> getQuoteById(@PathVariable Long id) {
        Optional<Quote> quote = quoteRepository.findById(id);
        return quote.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public Quote createQuote(@RequestBody Quote quote) {
        return quoteRepository.save(quote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quote> updateQuote(@PathVariable Long id, @RequestBody Quote quoteDetails) {
        Optional<Quote> quoteOptional = quoteRepository.findById(id);
        if (!quoteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Quote quote = quoteOptional.get();
        quote.setText(quoteDetails.getText());
        quote.setCharacter(quoteDetails.getCharacter());
        final Quote updatedQuote = quoteRepository.save(quote);
        return ResponseEntity.ok(updatedQuote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable Long id) {
        Optional<Quote> quoteOptional = quoteRepository.findById(id);
        if (!quoteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        quoteRepository.delete(quoteOptional.get());
        return ResponseEntity.noContent().build();
    }

    // Obtener 20 frases al azar
    @GetMapping("/random")
    public ResponseEntity<List<Quote>> getRandomQuotes() {
        List<Quote> randomQuotes = quoteRepository.findRandomQuotes();
        return ResponseEntity.ok(randomQuotes);
    }

}
