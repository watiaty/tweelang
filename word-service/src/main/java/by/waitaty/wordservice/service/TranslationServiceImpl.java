package by.waitaty.wordservice.service;

import by.waitaty.wordservice.model.Language;
import by.waitaty.wordservice.model.Word;
import by.waitaty.wordservice.repository.TranslationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TranslationServiceImpl implements TranslationService {
    private final TranslationRepository translationRepository;

    @Override
    public void delete(Long id) {
        translationRepository.deleteById(id);
    }

    @Override
    public void addWordTranslation(Word word, String translation, Language language) {

    }
}
