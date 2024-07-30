package by.waitaty.wordservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TranslationDtoResponse {
    private Long id;
    private String translation;
}
