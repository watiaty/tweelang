package by.waitaty.wordservice.dto;

import by.waitaty.wordservice.model.Translation;
import by.waitaty.wordservice.model.Word;
import by.waitaty.wordservice.model.WordUsage;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class Mapper {
    public WordDtoResponse wordUsageToWordDtoResponse(WordUsage wordUsage) {
        return WordDtoResponse.builder()
                .id(wordUsage.getId())
                .word(wordUsage.getWord().getText())
                .definition(wordUsage.getDefinition())
                .translations(wordUsage.getTranslations()
                        .stream()
                        .map(Translation::getTranslation)
                        .collect(Collectors.toList()))
                .build();
    }

    public ShortWordDtoResponse wordToShortWordDtoResponse(Word word) {
        return ShortWordDtoResponse.builder()
                .id(word.getId())
                .text(word.getText())
                .weight(word.getWeight())
                .build();
    }
}
