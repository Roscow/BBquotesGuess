package com.guessquotes.BreakingBadGuessQuotes.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.guessquotes.BreakingBadGuessQuotes.models.BBCharacter;
import com.guessquotes.BreakingBadGuessQuotes.models.Quote;
import com.guessquotes.BreakingBadGuessQuotes.DTOs.TriviaForm;
import com.guessquotes.BreakingBadGuessQuotes.models.TriviaResult;
import com.guessquotes.BreakingBadGuessQuotes.DTOs.ScoreForm;
import com.guessquotes.BreakingBadGuessQuotes.repository.BBCharacterRepository;
import com.guessquotes.BreakingBadGuessQuotes.repository.QuoteRepository;
import com.guessquotes.BreakingBadGuessQuotes.repository.TriviaResultRepository;
import com.guessquotes.BreakingBadGuessQuotes.repository.CountryRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

@Controller
public class TriviaController {

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private BBCharacterRepository characterRepository;

    @Autowired
    private TriviaResultRepository triviaResultRepository;

    @Autowired
    private CountryRepository countryRepository;

    @GetMapping("/trivia")
    public String getTrivia(Model model, HttpSession session) {
        List<Quote> quotes = quoteRepository.findRandomQuotes();  // Obtener 20 citas aleatorias
        List<BBCharacter> characters = characterRepository.findAll();
        session.setAttribute("triviaQuotes", quotes);  // Almacenar citas en la sesión
        model.addAttribute("quotes", quotes);
        model.addAttribute("characters", characters);
        model.addAttribute("triviaForm", new TriviaForm()); // Añadir el objeto triviaForm al modelo
        return "trivia";
    }

    @PostMapping("/trivia/submit")
    public String submitTrivia(@Valid @ModelAttribute TriviaForm triviaForm, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            List<Quote> quotes = (List<Quote>) session.getAttribute("triviaQuotes"); // Obtener las citas de la sesión
            List<BBCharacter> characters = characterRepository.findAll();
            model.addAttribute("quotes", quotes);
            model.addAttribute("characters", characters);
            model.addAttribute("triviaForm", triviaForm); // Volver a añadir el objeto triviaForm con los errores
            model.addAttribute("errorMessage", "All questions must be answered.");
            return "trivia";
        }

        List<Quote> quotes = (List<Quote>) session.getAttribute("triviaQuotes"); // Obtener las citas de la sesión
        List<TriviaForm.Answer> answers = triviaForm.getAnswers();
        String sessionId = UUID.randomUUID().toString(); // Generar un ID de sesión único

        // Guardar los resultados de la trivia
        for (int i = 0; i < answers.size(); i++) {
            TriviaResult result = new TriviaResult();
            result.setQuote(quotes.get(i));
            result.setSelectedCharacter(characterRepository.findById(answers.get(i).getCharacterId()).orElse(null));
            result.setSessionId(sessionId);
            triviaResultRepository.save(result);

            // Mensaje de depuración
            System.out.println("TriviaResult created: " + result.toString());
        }

        // Redirigir a la página de resultados con el ID de sesión
        return "redirect:/trivia/results?sessionId=" + sessionId;
    }
/*
    @GetMapping("/trivia/results")
    public String getResults(@RequestParam("sessionId") String sessionId, Model model) {
        List<TriviaResult> results = triviaResultRepository.findBySessionId(sessionId);
        int score = 0;
        List<Map<String, String>> displayResults = new ArrayList<>();
        for (TriviaResult result : results) {
            Map<String, String> displayResult = new HashMap<>();
            displayResult.put("quote", result.getQuote().getText());
            displayResult.put("userAnswer", result.getSelectedCharacter().getName());
            displayResult.put("correctAnswer", result.getQuote().getCharacter().getName());

            if (result.getSelectedCharacter().getId().equals(result.getQuote().getCharacter().getId())) {
                score += 100;  // 100 puntos por cada respuesta correcta
            }
            displayResults.add(displayResult);
        }
        // Añadir score, scoreForm y results al modelo
        model.addAttribute("score", score);
        model.addAttribute("scoreForm", new ScoreForm());
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("results", displayResults);
        return "results";
    }

 */

    @GetMapping("/trivia/results")
    public String getResults(@RequestParam("sessionId") String sessionId, Model model) {
        List<TriviaResult> results = triviaResultRepository.findBySessionId(sessionId);
        int score = 0;
        List<Map<String, String>> displayResults = new ArrayList<>();
        for (TriviaResult result : results) {
            Map<String, String> displayResult = new HashMap<>();
            displayResult.put("quote", result.getQuote().getText());
            displayResult.put("userAnswer", result.getSelectedCharacter().getName());

            // Indicar si la respuesta es correcta o incorrecta
            boolean isCorrect = result.getSelectedCharacter().getId().equals(result.getQuote().getCharacter().getId());
            displayResult.put("isCorrect", isCorrect ? "Correct" : "Incorrect");

            if (isCorrect) {
                score += 100;  // 100 puntos por cada respuesta correcta
            }
            displayResults.add(displayResult);
        }
        // Añadir score, scoreForm y results al modelo
        model.addAttribute("score", score);
        model.addAttribute("scoreForm", new ScoreForm());
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("results", displayResults);
        model.addAttribute("sessionId", sessionId);
        return "results";
    }

}
