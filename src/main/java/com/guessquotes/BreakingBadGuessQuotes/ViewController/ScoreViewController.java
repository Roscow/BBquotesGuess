package com.guessquotes.BreakingBadGuessQuotes.ViewController;
import com.guessquotes.BreakingBadGuessQuotes.models.TriviaResult;
import com.guessquotes.BreakingBadGuessQuotes.repository.TriviaResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.guessquotes.BreakingBadGuessQuotes.models.Score;
import com.guessquotes.BreakingBadGuessQuotes.DTOs.ScoreForm;
import com.guessquotes.BreakingBadGuessQuotes.repository.ScoreRepository;
import com.guessquotes.BreakingBadGuessQuotes.repository.CountryRepository;

import java.util.List;

@Controller
public class ScoreViewController {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private TriviaResultRepository triviaResultRepository;
/*
    @PostMapping("/scores/register")
    public String registerScore(@ModelAttribute ScoreForm scoreForm, Model model) {
        Score score = new Score();
        score.setName(scoreForm.getName());
        score.setCountry(countryRepository.findById(scoreForm.getCountryId()).orElse(null));
        score.setScore(scoreForm.getScore());
        score.setSessionId(scoreForm.getSessionId());

        scoreRepository.save(score);
        return "redirect:/";
    }

 */

    @PostMapping("/scores/register")
    public String registerScore(@ModelAttribute ScoreForm scoreForm, Model model) {
        List<TriviaResult> results = triviaResultRepository.findBySessionId(scoreForm.getSessionId());
        int score = 0;
        for (TriviaResult result : results) {
            if (result.getSelectedCharacter().getId().equals(result.getQuote().getCharacter().getId())) {
                score += 100;
            }
        }
        Score scoreEntity = new Score();
        scoreEntity.setName(scoreForm.getName());
        scoreEntity.setCountry(countryRepository.findById(scoreForm.getCountryId()).orElse(null));
        scoreEntity.setScore(score);
        scoreEntity.setSessionId(scoreForm.getSessionId());
        try {
            scoreRepository.save(scoreEntity);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("score", score);
            model.addAttribute("countries", countryRepository.findAll());
            model.addAttribute("results", results);
            return "scores";
        }
        return "redirect:/";
    }



    @GetMapping("")
    public String getScores(Model model) {
        model.addAttribute("scores", scoreRepository.findAllByOrderByScoreDesc());
        return "scores";
    }
}

