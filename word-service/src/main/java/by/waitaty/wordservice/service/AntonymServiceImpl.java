package by.waitaty.wordservice.service;

import by.waitaty.wordservice.model.Antonym;
import by.waitaty.wordservice.model.Word;
import by.waitaty.wordservice.repository.AntonymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class AntonymServiceImpl implements AntonymService {
    private final AntonymRepository antonymRepository;

    @Override
    public List<String> getAllAntonyms(Word word) {
        return Stream.concat(
                antonymRepository.findAllByAntonymWord(word).stream().map(antonym -> antonym.getWord().getWord()),
                antonymRepository.findAllByWord(word).stream().map(antonym -> antonym.getAntonymWord().getWord())
        ).distinct().toList();
    }
}
