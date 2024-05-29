package by.waitaty.wordservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AddTranslationRequest {
    private Long id;
    private String language;
    private String[] translations;
}
