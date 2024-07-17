package com.guessquotes.BreakingBadGuessQuotes.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.guessquotes.BreakingBadGuessQuotes.models.BBCharacter;
import com.guessquotes.BreakingBadGuessQuotes.repository.BBCharacterRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/characters")
public class BBCharacterController {

    @Autowired
    private BBCharacterRepository characterRepository;

    // Obtener todos los personajes
    @GetMapping
    public List<BBCharacter> getAllCharacters() {
        return characterRepository.findAll();
    }

    // Obtener un personaje por ID
    @GetMapping("/{id}")
    public ResponseEntity<BBCharacter> getCharacterById(@PathVariable Long id) {
        Optional<BBCharacter> character = characterRepository.findById(id);
        return character.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /*
    // Crear un nuevo personaje
    @PostMapping
    public ResponseEntity<BBCharacter> createCharacter(@RequestBody BBCharacter character) {
        BBCharacter savedCharacter = characterRepository.save(character);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCharacter);
    }

    // Actualizar un personaje existente
    @PutMapping("/{id}")
    public ResponseEntity<BBCharacter> updateCharacter(@PathVariable Long id, @RequestBody BBCharacter characterDetails) {
        Optional<BBCharacter> characterOptional = characterRepository.findById(id);
        if (!characterOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        BBCharacter character = characterOptional.get();
        character.setName(characterDetails.getName());
        final BBCharacter updatedCharacter = characterRepository.save(character);
        return ResponseEntity.ok(updatedCharacter);
    }

    // Eliminar un personaje
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Long id) {
        Optional<BBCharacter> characterOptional = characterRepository.findById(id);
        if (!characterOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        characterRepository.delete(characterOptional.get());
        return ResponseEntity.noContent().build();
    }

     */
}
