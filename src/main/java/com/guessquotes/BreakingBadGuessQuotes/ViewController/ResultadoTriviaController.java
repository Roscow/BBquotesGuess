package com.guessquotes.BreakingBadGuessQuotes.ViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.guessquotes.BreakingBadGuessQuotes.models.Country;
import com.guessquotes.BreakingBadGuessQuotes.repository.CountryRepository;

import java.util.List;

@Controller
public class ResultadoTriviaController {

    @Autowired
    private CountryRepository countryRepository;

    @GetMapping("/results")
    public String getResults(Model model) {
        int score = 100;  // Valor de prueba, este debería calcularse basándose en las respuestas del usuario
        List<Country> countries = countryRepository.findAll();
        model.addAttribute("score", score);
        model.addAttribute("countries", countries);
        return "results";
    }
}
