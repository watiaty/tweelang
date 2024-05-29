package by.waitaty.wordservice.service;

import by.waitaty.wordservice.model.Word;

import java.util.List;
import java.util.Optional;

public interface WordService {
    List<Word> findAll();

    Word addWord(Word word);

    Optional<Word> findWordByNameAndLang(String name, String lang);

    Word findWordById(Long id);

    Word findOrCreateWord(String name, String lang);
}
