package by.waitaty.wordservice.service;

import by.waitaty.wordservice.model.Word;
import by.waitaty.wordservice.model.WordForm;
import by.waitaty.wordservice.repository.WordFormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WordFormServiceImpl implements WordFormService{

    private final WordFormRepository wordFormRepository;

    @Override
    public List<String> getAllByMainWord(Word word) {
        return wordFormRepository.findAllByMainWord(word).stream().map(wordForm -> wordForm.getDerivedWord().getWord()).toList();
    }

    public String getMainWord(Word word) {
        return wordFormRepository.findByDerivedWord(word)
                .map(wf -> wf.getMainWord().getWord())
                .orElse(null);
    }
}
