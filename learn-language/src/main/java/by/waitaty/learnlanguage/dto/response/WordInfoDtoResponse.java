package by.waitaty.learnlanguage.dto.response;

import by.waitaty.learnlanguage.entity.Translation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class WordInfoDtoResponse {
    private String id;
    private String word;
    private String language;
    private List<Translation> translations;
}
