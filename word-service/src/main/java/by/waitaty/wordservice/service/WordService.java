package by.waitaty.wordservice.service;

import by.waitaty.wordservice.model.Word;

import java.util.Optional;

public interface WordService {
    Optional<Word> findWordByNameAndLanguage(String name, String language);

    Word findWordById(Long id);

    Word findOrCreateWord(String name, String language);
}
