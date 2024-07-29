package by.waitaty.wordservice.dto;

import by.waitaty.wordservice.model.Transcription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class WordWithTranslationsDto {
    private Long id;
    private String text;
    private String language;
    private List<Transcription> transcriptions;
    private List<WordUsageDtoResponse> wordUsages;
    private List<ShortWordDtoResponse> synonyms;
    private List<ShortWordDtoResponse> antonyms;
    private ShortWordDtoResponse formedBy;
    private int weight;
}
