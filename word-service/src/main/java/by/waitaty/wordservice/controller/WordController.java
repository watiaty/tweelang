package by.waitaty.wordservice.controller;

import by.waitaty.wordservice.dto.GetUserWordsRequest;
import by.waitaty.wordservice.dto.Mapper;
import by.waitaty.wordservice.dto.ShortWordDtoResponse;
import by.waitaty.wordservice.dto.WordDtoResponse;
import by.waitaty.wordservice.dto.WordUsageDtoResponse;
import by.waitaty.wordservice.dto.WordWithTranslationsDto;
import by.waitaty.wordservice.exception.UnsupportedFileException;
import by.waitaty.wordservice.exception.WordNotFoundException;
import by.waitaty.wordservice.model.Translation;
import by.waitaty.wordservice.model.Word;
import by.waitaty.wordservice.model.WordUsage;
import by.waitaty.wordservice.service.WordServiceImpl;
import by.waitaty.wordservice.service.WordUsageServiceImpl;
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
    private final WordUsageServiceImpl wordUsageService;

    private Mapper mapper;

    @GetMapping("/findById")
    public Word findById(@RequestParam("id") Long id) {
        return wordService.findWordById(id);
    }

    @GetMapping(path = "/all")
    public List<WordDtoResponse> getAll() {
        return null;
//                wordService.findAll().stream()
//                .map(mapper::wordToWordDtoResponse)
//                .toList();
    }

    @GetMapping(path = "/rating/{languageCode}")
    public List<ShortWordDtoResponse> getAllByRating(@PathVariable String languageCode) {
        return wordService.findAllByRatingAndLanguage(languageCode)
                .stream()
                .map(mapper::wordToShortWordDtoResponse)
                .toList();
    }

    @GetMapping(path = "/list")
    public List<WordDtoResponse> getByIds(@RequestBody GetUserWordsRequest request) {
        System.out.println(request.getIds());
        return wordUsageService.findListByIdsAndLanguage(request.getIds(), request.getLanguage())
                .stream()
                .map(mapper::wordUsageToWordDtoResponse)
                .toList();
    }

    @GetMapping(path = "/exclude")
    public WordDtoResponse findWordExcludingIdsByLanguage(@RequestBody GetUserWordsRequest request) {
        Word word = wordService.findWordExceptListByLanguage(request.getIds(), request.getLanguage());
        return WordDtoResponse.builder()
                .word(word.getText())
//                .id(word.getId())
//                .translations(translationService.findAllById(word.getId()))
                .build();
    }

    @GetMapping("/search")
    public List<ShortWordDtoResponse> search(@RequestParam("q") String searchText) {
        List<Word> words = wordService.searchWords(searchText);
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
    public Word findOrCreate(@RequestParam("word") String name, @RequestParam("lang") String lang) {
        return wordService.findOrCreateWord(name, lang);
    }

    @GetMapping("/{language}/{stringWord}")
    public WordWithTranslationsDto findByWordAndLang(@PathVariable String language, @PathVariable String stringWord) {
        Word word = wordService.findWordByNameAndLanguage(stringWord, language)
                .orElseThrow(() -> new WordNotFoundException(stringWord, language));

        List<WordUsageDtoResponse> wordUsageDtoResponses = word.getUsages().stream()
                .map(this::convertToWordUsageDtoResponse)
                .collect(Collectors.toList());

        return WordWithTranslationsDto.builder()
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

            String[] words = content.split("\\s+");
            for (var word : words) {
                var id = 1L;
//                var id = wordService.findOrCreateWord(word, "EN").getId();
                wordService.increaseWeight(id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private WordUsageDtoResponse convertToWordUsageDtoResponse(WordUsage wordUsage) {
        List<String> translations = wordUsage.getTranslations().stream()
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
