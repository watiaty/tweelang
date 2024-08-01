package by.waitaty.learnlanguage.client;

import by.waitaty.learnlanguage.config.FeignClientConfiguration;
import by.waitaty.learnlanguage.dto.request.GetUserWordsRequest;
import by.waitaty.learnlanguage.dto.response.WordDtoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "word-service", configuration = FeignClientConfiguration.class)
public interface WordClient {

    @PostMapping(value = "/api/v1/word/list")
    List<WordDtoResponse> getByIds(@RequestBody GetUserWordsRequest getUserWordsRequest);

    @GetMapping(value = "/api/v1/word/exclude")
    WordDtoResponse findWordExcludingIdsByLanguage(@RequestBody GetUserWordsRequest request);
}
