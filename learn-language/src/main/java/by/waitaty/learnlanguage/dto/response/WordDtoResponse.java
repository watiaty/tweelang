package by.waitaty.learnlanguage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class WordDtoResponse {
    private Long id;
    private String word;
    private String transcription;
    private String definition;
    private List<String> translations;
}
