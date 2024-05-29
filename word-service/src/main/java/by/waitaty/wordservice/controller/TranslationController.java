package by.waitaty.wordservice.controller;

import by.waitaty.wordservice.dto.AddTranslationRequest;
import by.waitaty.wordservice.dto.TranslationDto;
import by.waitaty.wordservice.model.Translation;
import by.waitaty.wordservice.model.Word;
import by.waitaty.wordservice.service.TranslationServiceImpl;
import by.waitaty.wordservice.service.WordServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.internal.util.StringHelper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @PostMapping("/")
    public List<TranslationDto> getAllTranslation(Long id) {
        var word = wordService.findWordById(id);
        return translationService.findAllByWord(word);
    }

    @Transactional
    @PostMapping("/add")
    public void addTranslation(@RequestBody AddTranslationRequest request) {
        var word = wordService.findById(request.getId());

        for (String translation : request.getTranslations()) {
            translationService.addWordTranslation(
                    wordService.findOrCreateWord(translation, request.getLanguage()),
                    word
            );
        }
    }
}
