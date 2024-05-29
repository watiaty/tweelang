package by.waitaty.wordservice.service;

import by.waitaty.wordservice.model.Word;

import java.util.List;

public interface SynonymService {
    List<String> getAllByWord(Word word);
}
