package com.guessquotes.BreakingBadGuessQuotes.ViewController;

import com.guessquotes.BreakingBadGuessQuotes.models.QuoteSuggestion;
import com.guessquotes.BreakingBadGuessQuotes.repository.QuoteSuggestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/suggestions")
public class QuoteSuggestionViewController {

    @Autowired
    private QuoteSuggestionRepository quoteSuggestionRepository;

    @GetMapping("")
    public String showSuggestionForm(Model model, @RequestParam(name = "success", required = false) String success) {
        model.addAttribute("quoteSuggestion", new QuoteSuggestion());
        List<QuoteSuggestion> suggestions = quoteSuggestionRepository.findAll();
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("success", success != null);
        return "suggestions";
    }

    @PostMapping("")
    public String submitSuggestion(@Valid @ModelAttribute QuoteSuggestion quoteSuggestion, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<QuoteSuggestion> suggestions = quoteSuggestionRepository.findAll();
            model.addAttribute("suggestions", suggestions);
            return "suggestions";
        }
        quoteSuggestionRepository.save(quoteSuggestion);
        return "redirect:/suggestions?success";
    }

    @PostMapping("/admin/{id}/status")
    public String updateSuggestionStatus(@PathVariable Long id, @RequestParam String status) {
        QuoteSuggestion suggestion = quoteSuggestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid suggestion Id:" + id));
        suggestion.setStatus(status);
        quoteSuggestionRepository.save(suggestion);
        return "redirect:/suggestions";
    }
}
