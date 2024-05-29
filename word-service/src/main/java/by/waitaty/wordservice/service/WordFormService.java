package by.waitaty.wordservice.service;

import by.waitaty.wordservice.model.Word;
import by.waitaty.wordservice.model.WordForm;

import java.util.List;

public interface WordFormService {
    List<String> getAllByMainWord(Word word);
}
