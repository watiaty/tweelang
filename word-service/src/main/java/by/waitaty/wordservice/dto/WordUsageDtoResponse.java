package by.waitaty.wordservice.dto;

import by.waitaty.wordservice.model.PartOfSpeech;
import by.waitaty.wordservice.model.ProficiencyLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class WordUsageDtoResponse {
    private Long id;
    private List<String> translations;
    private PartOfSpeech partOfSpeech;
    private ProficiencyLevel level;
    private String definition;
}
