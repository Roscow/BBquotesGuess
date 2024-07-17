package com.guessquotes.BreakingBadGuessQuotes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.guessquotes.BreakingBadGuessQuotes.models.Score;
import com.guessquotes.BreakingBadGuessQuotes.repository.ScoreRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    @Autowired
    private ScoreRepository scoreRepository;

    // Obtener todos los puntajes
    @GetMapping
    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    // Obtener un puntaje por ID
    @GetMapping("/{id}")
    public ResponseEntity<Score> getScoreById(@PathVariable Long id) {
        Optional<Score> score = scoreRepository.findById(id);
        return score.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Crear un nuevo puntaje
    @PostMapping
    public ResponseEntity<Score> createScore(@RequestBody Score score) {
        Score savedScore = scoreRepository.save(score);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedScore);
    }

    // Actualizar un puntaje existente
    @PutMapping("/{id}")
    public ResponseEntity<Score> updateScore(@PathVariable Long id, @RequestBody Score scoreDetails) {
        Optional<Score> scoreOptional = scoreRepository.findById(id);
        if (!scoreOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Score score = scoreOptional.get();
        score.setName(scoreDetails.getName());
        score.setCountry(scoreDetails.getCountry());
        score.setScore(scoreDetails.getScore());
        final Score updatedScore = scoreRepository.save(score);
        return ResponseEntity.ok(updatedScore);
    }

    // Eliminar un puntaje
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScore(@PathVariable Long id) {
        Optional<Score> scoreOptional = scoreRepository.findById(id);
        if (!scoreOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        scoreRepository.delete(scoreOptional.get());
        return ResponseEntity.noContent().build();
    }

    // Obtener los puntajes ordenados de mayor a menor
    @GetMapping("/top")
    public ResponseEntity<List<Score>> getTopScores() {
        List<Score> topScores = scoreRepository.findAllByOrderByScoreDesc();
        return ResponseEntity.ok(topScores);
    }
}
