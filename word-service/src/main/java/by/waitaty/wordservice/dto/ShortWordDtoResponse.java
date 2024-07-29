package by.waitaty.wordservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ShortWordDtoResponse {
    private Long id;
    private String text;
    private String language;
    private int weight;
}
