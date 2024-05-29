package by.waitaty.wordservice.dto;


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
    private String transcription;
    private String language;
    private List<TranslationDto> translations;
}
