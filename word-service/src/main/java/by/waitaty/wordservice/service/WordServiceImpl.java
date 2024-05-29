package by.waitaty.wordservice.service;

import by.waitaty.wordservice.model.Word;
import by.waitaty.wordservice.repository.WordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WordServiceImpl implements WordService {
    private final WordRepository wordRepository;

    @Override
    public List<Word> findAll() {
        return wordRepository.findAll();
    }

    public List<Word> findAllByRatingAndLanguage(String language) {
        return wordRepository.findAllByLanguageOrderByWeightDesc(language);
    }

    @Override
    public Word addWord(Word word) {
        return wordRepository.save(word);
    }

    @Override
    public Optional<Word> findWordByNameAndLang(String name, String language) {
        return wordRepository.findByWordAndLanguage(name, language);
    }

    @Override
    public Word findOrCreateWord(String name, String lang) {
        Optional<Word> existingWordOptional = wordRepository.findFirstByWordAndLanguage(name, lang);
        return existingWordOptional.orElseGet(() -> wordRepository.save(Word.builder()
                .word(name)
                .language(lang)
                .weight(1)
                .build()));
    }

    @Override
    public Word findWordById(Long id) {
        return wordRepository.findById(id).orElseThrow();
    }

    public List<Word> searchWords(String searchText) {
        return wordRepository.findAllByWordStartingWith(searchText);
    }

    public List<Word> findListByLang(List<Long> ids, String lang) {
        return wordRepository.findByIdInAndLanguage(ids, lang);
    }

    public Word findById(Long id) {
        return wordRepository.findById(id).orElseThrow();
    }


    public void increaseWeight(Long id) {
        wordRepository.incrementWeight(id);
    }

    public void delete(Long id) {
        wordRepository.deleteById(id);
    }
}
