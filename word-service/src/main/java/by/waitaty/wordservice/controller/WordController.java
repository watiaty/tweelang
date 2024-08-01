package by.waitaty.wordservice.controller;

import by.waitaty.wordservice.dto.request.GetUserWordsRequest;
import by.waitaty.wordservice.dto.Mapper;
import by.waitaty.wordservice.dto.response.ShortWordDtoResponse;
import by.waitaty.wordservice.dto.response.WordDtoResponseResponse;
import by.waitaty.wordservice.dto.response.WordUsageDtoResponse;
import by.waitaty.wordservice.dto.response.WordWithTranslationsDtoResponse;
import by.waitaty.wordservice.exception.UnsupportedFileException;
import by.waitaty.wordservice.exception.WordNotFoundException;
import by.waitaty.wordservice.model.Translation;
import by.waitaty.wordservice.model.Word;
import by.waitaty.wordservice.model.WordUsage;
import by.waitaty.wordservice.service.impl.LanguageServiceImpl;
import by.waitaty.wordservice.service.impl.WordServiceImpl;
import by.waitaty.wordservice.service.impl.WordUsageServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/word")
@AllArgsConstructor
public class WordController {
    private final WordServiceImpl wordService;
    private final LanguageServiceImpl languageService;
    private final WordUsageServiceImpl wordUsageService;

    private Mapper mapper;

    @GetMapping("/findById")
    public Word findById(@RequestParam("id") Long id) {
        return wordService.getById(id);
    }

    @GetMapping(path = "/rating/{languageCode}")
    public List<ShortWordDtoResponse> getAllByRating(@PathVariable String languageCode) {
        return wordService.getAllByRatingAndLanguage(languageCode)
                .stream()
                .map(mapper::wordToShortWordDtoResponse)
                .toList();
    }

    @PostMapping(path = "/list")
    public List<WordDtoResponseResponse> getByIds(@RequestBody GetUserWordsRequest request) {
        return wordUsageService.getListByIdsAndLanguage(request.getIds(), request.getLanguage())
                .stream()
                .map(mapper::wordUsageToWordDtoResponse)
                .toList();
    }

    @GetMapping(path = "/exclude")
    public WordDtoResponseResponse findWordExcludingIdsByLanguage(@RequestBody GetUserWordsRequest request) {
        var word = wordService.getWordExceptListByLanguage(request.getIds(), request.getLanguage());
        return WordDtoResponseResponse.builder()
                .word(word.getText())
                .id(word.getId())
//                .translations(translationService.findAllById(word.getId()))
                .build();
    }

    @GetMapping("/search")
    public List<ShortWordDtoResponse> search(@RequestParam("q") String searchText) {
        var words = wordService.searchWords(searchText);
        return words.stream()
                .map(word -> ShortWordDtoResponse.builder()
                        .id(word.getId())
                        .text(word.getText())
                        .language(word.getLanguage().getCode())
                        .weight(word.getWeight())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/find")
    public Word findOrCreate(@RequestParam("word") String name, @RequestParam("lang") String languageCode) {
        var language = languageService.getByCode(languageCode);
        return wordService.getOrCreateWord(name, language);
    }

    @GetMapping("/{language}/{stringWord}")
    public WordWithTranslationsDtoResponse findByWordAndLang(@PathVariable String language, @PathVariable String stringWord) {
        var word = wordService.getWordByNameAndLanguage(stringWord, language)
                .orElseThrow(() -> new WordNotFoundException(stringWord, language));

        var wordUsageDtoResponses = word.getUsages().stream()
                .map(this::convertToWordUsageDtoResponse)
                .collect(Collectors.toList());

        return WordWithTranslationsDtoResponse.builder()
                .id(word.getId())
                .text(word.getText())
                .language(word.getLanguage().getCode())
                .transcriptions(word.getTranscriptions())
                .wordUsages(wordUsageDtoResponses)
                .synonyms(word.getRelations().stream().filter(relation -> relation.getType().equals("synonym")).map(relation -> ShortWordDtoResponse.builder()
                        .text(relation.getRelatedWord().getText())
                        .weight(relation.getRelatedWord().getWeight())
                        .build()).collect(Collectors.toList()))
                .antonyms(word.getRelations().stream().filter(relation -> relation.getType().equals("antonym")).map(relation -> ShortWordDtoResponse.builder()
                        .text(relation.getRelatedWord().getText())
                        .weight(relation.getRelatedWord().getWeight())
                        .build()).collect(Collectors.toList()))
                .formedBy(word.getRelations().stream().filter(relation -> relation.getType().equals("primary")).findFirst().map(relation -> ShortWordDtoResponse.builder()
                        .text(relation.getRelatedWord().getText())
                        .weight(relation.getRelatedWord().getWeight())
                        .build()).orElse(null))
                .weight(word.getWeight())
                .build();
    }

    @DeleteMapping("/{id}")
    public void deleteWordById(@PathVariable Long id) {
        wordService.delete(id);
    }

    @PostMapping("/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".txt")) {
            throw new UnsupportedFileException(file.getOriginalFilename());
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            var content = br.lines()
                    .map(line -> line.replaceAll("[\\p{Punct}&&[^']]", "").toLowerCase())
                    .collect(Collectors.joining(" "));

            var words = content.split("\\s+");
            for (var word : words) {
                var language = languageService.getByCode("en");
                var id = wordService.getOrCreateWord(word, language).getId();
                wordService.increaseWeight(id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private WordUsageDtoResponse convertToWordUsageDtoResponse(WordUsage wordUsage) {
        var translations = wordUsage.getTranslations().stream()
                .filter(translation -> Objects.equals(translation.getLanguage().getCode(), "ru"))
                .map(Translation::getTranslation)
                .collect(Collectors.toList());

        return WordUsageDtoResponse.builder()
                .id(wordUsage.getId())
                .translations(translations)
                .partOfSpeech(wordUsage.getPartOfSpeech())
                .level(wordUsage.getLevel())
                .definition(wordUsage.getDefinition())
                .build();
    }
}
