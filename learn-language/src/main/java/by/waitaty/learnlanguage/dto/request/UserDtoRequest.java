package by.waitaty.learnlanguage.dto.request;

import by.waitaty.learnlanguage.entity.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class UserDtoRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String nativeLang;
    private List<String> learningLangs;
}
