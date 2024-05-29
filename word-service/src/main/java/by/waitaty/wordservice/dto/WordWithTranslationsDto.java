package by.waitaty.wordservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WordWithTranslationsDto {
    private Long id;
    private String word;
    private String language;
    private String definition;
    private List<TranslationDto> translations;
    private List<String> antonyms;
    private List<String> synonyms;
    private List<String> derived;
    private String mainWord;
}
