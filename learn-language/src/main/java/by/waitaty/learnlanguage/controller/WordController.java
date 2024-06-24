package by.waitaty.learnlanguage.controller;

import by.waitaty.learnlanguage.client.WordClient;
import by.waitaty.learnlanguage.dto.Mapper;
import by.waitaty.learnlanguage.dto.TranslationDto;
import by.waitaty.learnlanguage.dto.WordDtoResponse;
import by.waitaty.learnlanguage.dto.request.AddNewTranslationRequest;
import by.waitaty.learnlanguage.dto.request.AddUserWordDtoRequest;
import by.waitaty.learnlanguage.dto.request.AddWordRequest;
import by.waitaty.learnlanguage.dto.request.GetUserWordsRequest;
import by.waitaty.learnlanguage.dto.request.WordTrainDtoRequest;
import by.waitaty.learnlanguage.dto.response.UserWordDtoResponse;
import by.waitaty.learnlanguage.entity.Language;
import by.waitaty.learnlanguage.entity.UserWord;
import by.waitaty.learnlanguage.entity.Word;
import by.waitaty.learnlanguage.entity.WordStatus;
import by.waitaty.learnlanguage.service.impl.UserWordServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/words")
@AllArgsConstructor
public class WordController {
    private final UserWordServiceImpl userWordService;
    private Mapper mapper;
    private LoadBalancedExchangeFilterFunction filterFunction;

    @GetMapping(path = "/{lang}")
    @SecurityRequirement(name = "JWT")
    @ResponseBody
    public List<WordDtoResponse> getAll(
            @PathVariable Language lang,
            @AuthenticationPrincipal Jwt jwt,
            @RegisteredOAuth2AuthorizedClient("messaging-client-token-exchange") OAuth2AuthorizedClient authorizedClient) {
        Long userId = jwt.getClaim("userId");
        List<Long> idList = userWordService.getAllByUserId(userId).stream()
                .map(UserWord::getIdWord)
                .toList();

        WordClient wordClient = getWordClient(authorizedClient);

        return wordClient.getByIds(GetUserWordsRequest.builder()
                .ids(idList)
                .language(lang.name())
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

        WordClient wordClient = getWordClient(authorizedClient);

        return wordClient.findWordExcludingIdsByLanguage(GetUserWordsRequest.builder()
                .ids(idList)
                .language(lang.name())
                .build());
    }

    @PostMapping(path = "/practice")
    @SecurityRequirement(name = "JWT")
    @ResponseBody
    public List<UserWordDtoResponse> getWordsForTraining(
            @RequestBody WordTrainDtoRequest wordTrainDtoRequest,
            @AuthenticationPrincipal Jwt jwt,
            @RegisteredOAuth2AuthorizedClient("messaging-client-token-exchange") OAuth2AuthorizedClient authorizedClient) {
        Long userId = jwt.getClaim("userId");
        List<Long> wordIds = getLearningWordIds(wordTrainDtoRequest.getQuantity(), userId);

        List<UserWordDtoResponse> userWordDtoResponses = fetchWordsFromService(wordIds, authorizedClient);

        Collections.shuffle(userWordDtoResponses);

        return userWordDtoResponses;
    }

    /**
     * Retrieves a list of word IDs for learning.
     * @param quantity the number of words to retrieve
     * @param userId the ID of the user
     * @return a list of word IDs
     */
    private List<Long> getLearningWordIds(int quantity, Long userId) {
        return userWordService.getCountLearningWords(quantity, userId)
                .stream()
                .map(UserWord::getIdWord)
                .collect(Collectors.toList());
    }

    /**
     * Fetches words from an external service.
     * @param wordIds the list of word IDs
     * @param authorizedClient the authorized client for interacting with the service
     * @return a list of user word DTO responses
     */
    private List<UserWordDtoResponse> fetchWordsFromService(List<Long> wordIds, OAuth2AuthorizedClient authorizedClient) {
        WordClient wordClient = getWordClient(authorizedClient);
        return wordClient.getByIds(GetUserWordsRequest.builder()
                        .ids(wordIds)
                        .language("EN")
                        .build())
                .stream()
                .map(wordDtoResponse -> new UserWordDtoResponse(
                        wordDtoResponse.getId(),
                        wordDtoResponse.getWord(),
                        wordDtoResponse.getTranslations().stream().map(TranslationDto::getTranslation).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @PatchMapping("/update/{id}")
    public void setLearnedStatusWord(@PathVariable Long id, @RequestBody String status, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        userWordService.findByIdWordAndIdUser(id, userId).ifPresent(userWord -> {
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
        userWordService.delete(id, userId);
    }

    @PostMapping("/add")
    @SecurityRequirement(name = "JWT")
    @Transactional
    public ResponseEntity<UserWordDtoResponse> addWord(
            @RequestBody AddWordRequest addWordRequest,
            @AuthenticationPrincipal Jwt jwt,
            @RegisteredOAuth2AuthorizedClient("messaging-client-token-exchange") OAuth2AuthorizedClient authorizedClient) {
        Long userId = jwt.getClaim("userId");
        WordClient wordClient = getWordClient(authorizedClient);
        Word word = wordClient.findOrCreate(addWordRequest.getWord(), Language.getLanguageFromString(addWordRequest.getLanguage()).name());
        UserWord userWord = createUserWord(userId, word, addWordRequest.getTranslations(), wordClient);
        UserWord savedUserWord = userWordService.update(userWord);
        return ResponseEntity.ok(mapper.userWordToWordDtoResponse(savedUserWord));
    }

    @PostMapping("/add-new-word")
    @SecurityRequirement(name = "JWT")
    @Transactional
    public void addWordByIdAndStatus(
            @RequestBody AddUserWordDtoRequest addWordRequest,
            @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        userWordService.update(UserWord.builder()
                        .idUser(userId)
                        .idWord(addWordRequest.getId())
                        .status(addWordRequest.getStatus())
                .build());
    }

    @PostMapping("/csv")
    @SecurityRequirement(name = "JWT")
    @Transactional
    public void addWordsFromCsv(
            @AuthenticationPrincipal Jwt jwt,
            @RegisteredOAuth2AuthorizedClient("messaging-client-token-exchange") OAuth2AuthorizedClient authorizedClient) {
        Long userId = jwt.getClaim("userId");
        WordClient wordClient = getWordClient(authorizedClient);
        String csvFile = "D:\\Java\\Projects\\tweelang\\learn-language\\src\\main\\resources\\words.csv";
        String line;
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] data = line.split(cvsSplitBy);
                String englishWord = data[0].trim();
                String russianTranslation = data[1].trim();

                // Call method to insert data into database
                Word word = wordClient.findOrCreate(englishWord, "EN");
                String[] translations = new String[1];
                translations[0] = russianTranslation;
                UserWord userWord = createUserWord(userId, word, translations, wordClient);
                UserWord savedUserWord = userWordService.update(userWord);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UserWord createUserWord(Long userId, Word word, String[] translations, WordClient wordClient) {
        UserWord userWord = userWordService.getUserWordByWord(word.getId(), userId)
                .orElseGet(() -> UserWord.builder()
                        .idUser(userId)
                        .idWord(word.getId())
                        .repeatStage(1)
                        .repeatDate(LocalDate.now())
                        .build());

        wordClient.addTranslation(AddNewTranslationRequest.builder()
                .id(word.getId())
                .language(Language.RU.name())
                .translations(translations)
                .build()
        );

        return userWord;
    }

    public WordClient getWordClient(OAuth2AuthorizedClient authorizedClient) {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://word-service")
                .filter(filterFunction)
                .defaultHeaders((headers) -> headers.setBearerAuth(authorizedClient.getAccessToken().getTokenValue()))
                .build();

        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();
        return httpServiceProxyFactory.createClient(WordClient.class);
    }
}
