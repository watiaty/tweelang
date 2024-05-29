package by.waitaty.learnlanguage.dto;

import by.waitaty.learnlanguage.dto.response.TranslationSummaryDtoResponse;
import by.waitaty.learnlanguage.dto.response.UserWordDtoResponse;
import by.waitaty.learnlanguage.entity.Translation;
import by.waitaty.learnlanguage.entity.UserWord;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public UserWordDtoResponse userWordToWordDtoResponse(UserWord userWord) {
        return UserWordDtoResponse.builder()
                .id(userWord.getId())
                .word(userWord.getIdWord().toString())
//                .translations(userWord.getTranslations().stream().map(Translation::toString).toList())
                .build();
    }

    public TranslationSummaryDtoResponse wordTranslationSummaryDtoResponse(Translation translation, boolean contains) {
        return new TranslationSummaryDtoResponse(
//                translation.getId(), translation.getIdTranslationWord(), contains, translation.getNumberOfUses()
        );
    }
}
