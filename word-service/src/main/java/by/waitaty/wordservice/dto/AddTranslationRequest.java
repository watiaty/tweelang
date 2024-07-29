package by.waitaty.wordservice.dto;

import by.waitaty.wordservice.model.Language;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AddTranslationRequest {
    private Long id;
    private Language language;
    private String[] translations;
}
