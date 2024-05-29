package by.waitaty.wordservice.service;

import by.waitaty.wordservice.model.Word;

import java.util.List;

public interface AntonymService {
    List<String> getAllAntonyms(Word word);
}
