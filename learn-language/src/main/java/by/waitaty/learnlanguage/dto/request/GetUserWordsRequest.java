package by.waitaty.learnlanguage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserWordsRequest {
    private List<Long> ids;
    private String language;
}
