package by.waitaty.learnlanguage.client;

import by.waitaty.learnlanguage.dto.request.GetUserWordsRequest;
import by.waitaty.learnlanguage.dto.response.WordDtoResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface WordClient {
    @GetExchange("/api/v1/word/list")
    List<WordDtoResponse> getByIds(@RequestBody GetUserWordsRequest getUserWordsRequest);

    @GetExchange("/api/v1/word/exclude")
    WordDtoResponse findWordExcludingIdsByLanguage(@RequestBody GetUserWordsRequest request);
}
