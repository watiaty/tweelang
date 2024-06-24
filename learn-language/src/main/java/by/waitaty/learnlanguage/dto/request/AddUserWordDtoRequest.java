package by.waitaty.learnlanguage.dto.request;

import by.waitaty.learnlanguage.entity.WordStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddUserWordDtoRequest {
    private Long id;
    private WordStatus status;
}
