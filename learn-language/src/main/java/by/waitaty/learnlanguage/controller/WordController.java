package by.waitaty.learnlanguage.controller;

import by.waitaty.learnlanguage.client.WordClient;
import by.waitaty.learnlanguage.config.WebClientConfig;
import by.waitaty.learnlanguage.dto.request.AddUserWordDtoRequest;
import by.waitaty.learnlanguage.dto.request.GetUserWordsRequest;
import by.waitaty.learnlanguage.dto.request.WordPracticeDtoRequest;
import by.waitaty.learnlanguage.dto.response.WordDtoResponse;
import by.waitaty.learnlanguage.entity.Language;
import by.waitaty.learnlanguage.entity.UserWord;
import by.waitaty.learnlanguage.service.impl.UserWordServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/words")
@AllArgsConstructor
public class WordController {
    private final UserWordServiceImpl userWordService;
    private WebClientConfig webClient;

    @GetMapping(path = "/{language}")
    @SecurityRequirement(name = "JWT")
    @ResponseBody
    public List<WordDtoResponse> getAll(
            @PathVariable String language,
            @AuthenticationPrincipal Jwt jwt,
            @RegisteredOAuth2AuthorizedClient("messaging-client-token-exchange") OAuth2AuthorizedClient authorizedClient) {
        Long userId = jwt.getClaim("userId");
        List<Long> idList = userWordService.getAllByUserId(userId).stream()
                .map(UserWord::getIdWord)
                .toList();

        WordClient wordClient = webClient.wordClient(authorizedClient);

        return wordClient.getByIds(GetUserWordsRequest.builder()
                .ids(idList)
                .language(language)
                .build());
    }

    @GetMapping(path = "/learn/{lang}")
    @SecurityRequirement(name = "JWT")
    @ResponseBody
    public WordDtoResponse getUnstudiedWord(
            @PathVariable Language lang,
            @AuthenticationPrincipal Jwt jwt,
            @RegisteredOAuth2AuthorizedClient("messaging-client-token-exchange") OAuth2AuthorizedClient authorizedClient) {
        Long userId = jwt.getClaim("userId");
        List<Long> idList = userWordService.getAllByUserId(userId).stream()
                .map(UserWord::getIdWord)
                .toList();

        WordClient wordClient = webClient.wordClient(authorizedClient);

        return wordClient.findWordExcludingIdsByLanguage(GetUserWordsRequest.builder()
                .ids(idList)
                .language(lang.name())
                .build());
    }

    @PostMapping(path = "/practice")
    @SecurityRequirement(name = "JWT")
    @ResponseBody
    public List<WordDtoResponse> getWordsForTraining(
            @RequestBody WordPracticeDtoRequest wordPracticeDtoRequest,
            @AuthenticationPrincipal Jwt jwt,
            @RegisteredOAuth2AuthorizedClient("messaging-client-token-exchange") OAuth2AuthorizedClient authorizedClient) {
        Long userId = jwt.getClaim("userId");
        List<Long> wordIds = getLearningWordIds(wordPracticeDtoRequest.getQuantity(), userId);
        List<WordDtoResponse> wordDtoResponses = fetchWordsFromService(wordIds, authorizedClient);

        Collections.shuffle(wordDtoResponses);

        return wordDtoResponses;
    }

    @PatchMapping("/update/{id}")
    public void setLearnedStatusWord(@PathVariable Long id, @RequestBody String status, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        userWordService.getByWordIdAndUserId(id, userId).ifPresent(userWord -> {
            userWord.setRepeatDate(LocalDate.now());
            if (status.equals("learned")) {
                if (userWord.getRepeatStage() < 5) {
                    userWord.setRepeatStage(userWord.getRepeatStage() + 1);
                }
            } else {
                userWord.setRepeatStage(1);
                userWord.setRepeatDate(LocalDate.now());
            }

            userWordService.update(userWord);
        });
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    @SecurityRequirement(name = "JWT")
    public void deleteUserWord(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        userWordService.deleteByIdAndUserId(id, userId);
    }

    @PostMapping("/add-new-word")
    @SecurityRequirement(name = "JWT")
    @Transactional
    public void addWordByIdAndStatus(
            @RequestBody AddUserWordDtoRequest addWordRequest,
            @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        Long wordId = addWordRequest.getId();

        if (userWordService.existsByUserIdAndWordId(userId, wordId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Word already added");
        }

        userWordService.update(UserWord.builder()
                .idUser(userId)
                .idWord(wordId)
                .repeatStage(1)
                .status(addWordRequest.getStatus())
                .build());
    }

    /**
     * Retrieves a list of word IDs for learning.
     *
     * @param quantity the number of words to retrieve
     * @param userId   the ID of the user
     * @return a list of word IDs
     */
    private List<Long> getLearningWordIds(int quantity, Long userId) {
        return userWordService.getQuantityLearningWordsByUserId(quantity, userId)
                .stream()
                .map(UserWord::getIdWord)
                .collect(Collectors.toList());
    }

    /**
     * Fetches words from an external service.
     *
     * @param wordIds          the list of word IDs
     * @param authorizedClient the authorized client for interacting with the service
     * @return a list of user word DTO responses
     */
    private List<WordDtoResponse> fetchWordsFromService(List<Long> wordIds, OAuth2AuthorizedClient authorizedClient) {
        WordClient wordClient = webClient.wordClient(authorizedClient);
        return wordClient.getByIds(GetUserWordsRequest.builder()
                .ids(wordIds)
                .language("en")
                .build());
    }
}
