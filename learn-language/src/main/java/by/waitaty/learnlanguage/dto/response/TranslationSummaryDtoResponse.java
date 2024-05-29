package by.waitaty.learnlanguage.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TranslationSummaryDtoResponse {
    private Long id;
    private Long translation;
    private boolean isUser;
    private Long quantity;
}
