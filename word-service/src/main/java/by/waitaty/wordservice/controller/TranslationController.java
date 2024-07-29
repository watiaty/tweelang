package by.waitaty.wordservice.controller;

import by.waitaty.wordservice.dto.AddTranslationRequest;
import by.waitaty.wordservice.service.TranslationServiceImpl;
import by.waitaty.wordservice.service.WordServiceImpl;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/translation")
@AllArgsConstructor
public class TranslationController {
    private final TranslationServiceImpl translationService;
    private final WordServiceImpl wordService;

    @Transactional
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteTranslation(@PathVariable Long id) {
        translationService.delete(id);
    }

    @Transactional
    @PostMapping("/add")
    public void addTranslation(@RequestBody AddTranslationRequest request) {
        var word = wordService.findById(request.getId());

        for (String translation : request.getTranslations()) {
            translationService.addWordTranslation(word, translation, request.getLanguage());
        }
    }
}
