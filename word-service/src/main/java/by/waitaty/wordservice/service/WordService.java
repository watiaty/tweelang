package by.waitaty.wordservice.service;

import by.waitaty.wordservice.model.Language;
import by.waitaty.wordservice.model.Word;

import java.util.List;
import java.util.Optional;

public interface WordService {
    Word getById(Long id);

    void delete(Long id);

    List<Word> searchWords(String searchText);

    Word getOrCreateWord(String name, Language language);

    Optional<Word> getWordByNameAndLanguage(String name, String languageCode);

    List<Word> getAllByRatingAndLanguage(String languageCode);

    Word getWordExceptListByLanguage(List<Long> ids, String language);

    void increaseWeight(Long id);
}
