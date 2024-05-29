package by.waitaty.learnlanguage.client;

import by.waitaty.learnlanguage.dto.WordDtoResponse;
import by.waitaty.learnlanguage.dto.request.AddNewTranslationRequest;
import by.waitaty.learnlanguage.dto.request.GetUserWordsRequest;
import by.waitaty.learnlanguage.entity.Word;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange
public interface WordClient {
    @GetExchange("/api/v1/word/find")
    Word findOrCreate(@RequestParam("word") String name, @RequestParam("lang") String lang);

    @GetExchange("/api/v1/word/findbywordandlang")
    Word findByWordAndLang(@RequestParam("word") String name, @RequestParam("lang") String lang);

    @GetExchange("/findbyid")
    Word findById(Long id);

    @GetExchange("/api/v1/word/list")
    List<WordDtoResponse> getByIds(@RequestBody GetUserWordsRequest getUserWordsRequest);

    @PostExchange("/api/v1/translation/add")
    void addTranslation(@RequestBody AddNewTranslationRequest request);
}
