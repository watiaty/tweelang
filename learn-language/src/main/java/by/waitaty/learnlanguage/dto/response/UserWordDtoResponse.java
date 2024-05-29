package by.waitaty.learnlanguage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserWordDtoResponse {
    private Long id;
    private String word;
    private List<String> translations;
}
