package by.waitaty.learnlanguage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordPracticeDtoRequest {
    private int quantity;
    private String language;
}
