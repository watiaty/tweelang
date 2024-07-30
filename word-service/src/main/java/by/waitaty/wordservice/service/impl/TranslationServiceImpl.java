package by.waitaty.wordservice.service.impl;

import by.waitaty.wordservice.model.Language;
import by.waitaty.wordservice.model.Word;
import by.waitaty.wordservice.repository.TranslationRepository;
import by.waitaty.wordservice.service.TranslationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TranslationServiceImpl implements TranslationService {
    private final TranslationRepository translationRepository;

    public void delete(Long id) {
        translationRepository.deleteById(id);
    }

    public void addWordTranslation(Word word, String translation, Language language) {

    }
}
