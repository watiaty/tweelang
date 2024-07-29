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

    public List<Word> findAllByRatingAndLanguage(String languageCode) {
        return wordRepository.findAllByLanguageOrderByWeightDesc(languageCode);
    }

    public Optional<Word> findWordByNameAndLanguage(String name, String languageCode) {
        return wordRepository.findByTextAndLanguage_Code(name, languageCode);
    }

    @Override
    public Word findOrCreateWord(String name, String language) {
        Optional<Word> existingWordOptional = wordRepository.findFirstByTextAndLanguage_Name(name, language);
        return existingWordOptional.orElseGet(() -> wordRepository.save(Word.builder()
                .text(name)
//                .language(language)
                .weight(1)
                .build()));
    }

    @Override
    public Word findWordById(Long id) {
        return wordRepository.findById(id).orElseThrow();
    }

    public List<Word> searchWords(String searchText) {
        return wordRepository.findAllByTextStartingWith(searchText);
    }

    public Word findWordExceptListByLanguage(List<Long> ids, String language) {
        return wordRepository.findFirstByIdNotInAndLanguage_Name(ids, language);
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
