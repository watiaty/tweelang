package by.waitaty.wordservice.service;

import by.waitaty.wordservice.model.Language;
import by.waitaty.wordservice.model.Word;

public interface TranslationService {
    void delete(Long id);

    void addWordTranslation(Word word, String translation, Language language);
}
