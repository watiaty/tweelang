package by.waitaty.learnlanguage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TranslationDto {
    private Long id;
    private String translation;
    private Long quantity;
}
