package by.waitaty.wordservice.service;

import by.waitaty.wordservice.model.Word;
import by.waitaty.wordservice.repository.SynonymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class SynonymServiceImpl implements SynonymService {
    private final SynonymRepository synonymRepository;

    @Override
    public List<String> getAllByWord(Word word) {
        return Stream.concat(
                synonymRepository.findAllByWord(word).stream().map(synonym -> synonym.getSynonymWord().getWord()),
                synonymRepository.findAllBySynonymWord(word).stream().map(synonym -> synonym.getWord().getWord())
        ).distinct().toList();
    }
}
