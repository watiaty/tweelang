package by.waitaty.wordservice.service;

import by.waitaty.wordservice.dto.TranslationDto;
import by.waitaty.wordservice.model.Translation;
import by.waitaty.wordservice.model.Word;
import by.waitaty.wordservice.repository.TranslationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TranslationServiceImpl implements TranslationService {
    private final TranslationRepository translationRepository;

    @Override
    public void addWordTranslation(Word translation, Word word) {
        translationRepository.findByTranslatedWordAndWord(translation, word).orElseGet(() ->
                translationRepository.save(Translation.builder()
                        .translatedWord(translation)
                        .word(word)
                        .build()));
    }

    @Override
    public void delete(Long id) {
        translationRepository.deleteById(id);
    }

    @Override
    public List<TranslationDto> findAllByWord(Word word) {
        return translationRepository.findAllByWord(word);
    }

    public List<TranslationDto> findAllById(Long id) {
        return translationRepository.findAllById(id);
    }
}
