package by.waitaty.wordservice.service.impl;

import by.waitaty.wordservice.model.Language;
import by.waitaty.wordservice.model.Word;
import by.waitaty.wordservice.repository.WordRepository;
import by.waitaty.wordservice.service.WordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WordServiceImpl implements WordService {
    private final WordRepository wordRepository;

    public Word getById(Long id) {
        return wordRepository.findById(id).orElseThrow();
    }

    public void delete(Long id) {
        wordRepository.deleteById(id);
    }

    public List<Word> searchWords(String searchText) {
        return wordRepository.findAllByTextStartingWith(searchText);
    }

    public Word getOrCreateWord(String name, Language language) {
        var existingWordOptional = wordRepository.findFirstByTextAndLanguage(name, language);
        return existingWordOptional.orElseGet(() -> wordRepository.save(Word.builder()
                .text(name)
                .language(language)
                .weight(1)
                .build()));
    }

    public Optional<Word> getWordByNameAndLanguage(String name, String languageCode) {
        return wordRepository.findByTextAndLanguageCode(name, languageCode);
    }

    public List<Word> getAllByRatingAndLanguage(String languageCode) {
        return wordRepository.findAllByLanguageOrderByWeightDesc(languageCode);
    }

    public Word getWordExceptListByLanguage(List<Long> ids, String language) {
        return wordRepository.findFirstByIdNotInAndLanguageName(ids, language);
    }

    public void increaseWeight(Long id) {
        wordRepository.incrementWeight(id);
    }
}
