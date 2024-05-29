package by.waitaty.learnlanguage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordTrainDtoRequest {
    private int quantity;
    private String language;
}
