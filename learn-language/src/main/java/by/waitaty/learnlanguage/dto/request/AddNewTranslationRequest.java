package by.waitaty.learnlanguage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddNewTranslationRequest {
    private Long id;
    private String language;
    private String[] translations;
}
