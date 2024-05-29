package by.waitaty.wordservice.dto;

import by.waitaty.wordservice.model.Word;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public WordDtoResponse wordToWordDtoResponse(Word word) {
        return WordDtoResponse.builder()
                .id(word.getId())
                .word(word.getWord())
                .build();
    }
}
