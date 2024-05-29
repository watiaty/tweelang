package by.waitaty.learnlanguage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddWordRequest {
    private String word;
    private String language;
    private String[] translations;
}
