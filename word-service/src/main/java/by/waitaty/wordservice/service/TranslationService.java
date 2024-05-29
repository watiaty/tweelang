package by.waitaty.wordservice.service;

import by.waitaty.wordservice.dto.TranslationDto;
import by.waitaty.wordservice.model.Word;

import java.util.List;

public interface TranslationService {
    void addWordTranslation(Word translation, Word word);

    void delete(Long id);

    List<TranslationDto> findAllByWord(Word word);
}
