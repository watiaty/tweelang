package by.waitaty.wordservice.controller;

import by.waitaty.wordservice.dto.GetUserWordsRequest;
import by.waitaty.wordservice.dto.Mapper;
import by.waitaty.wordservice.dto.WordDtoResponse;
import by.waitaty.wordservice.dto.WordInfoDtoResponse;
import by.waitaty.wordservice.dto.WordWithTranslationsDto;
import by.waitaty.wordservice.exception.UnsupportedFileException;
import by.waitaty.wordservice.exception.WordNotFoundException;
import by.waitaty.wordservice.model.Word;
import by.waitaty.wordservice.service.AntonymServiceImpl;
import by.waitaty.wordservice.service.SynonymServiceImpl;
import by.waitaty.wordservice.service.TranslationServiceImpl;
import by.waitaty.wordservice.service.WordFormServiceImpl;
import by.waitaty.wordservice.service.WordServiceImpl;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/word")
@AllArgsConstructor
public class WordController {
    private final WordServiceImpl wordService;
    private final TranslationServiceImpl translationService;
    private final AntonymServiceImpl antonymService;
    private final SynonymServiceImpl synonymService;
    private final WordFormServiceImpl wordFormService;
    private Mapper mapper;

    @GetMapping("/findById")
    public Word findById(@RequestParam("id") Long id) {
        return wordService.findWordById(id);
    }

    @GetMapping(path = "/all")
    public List<WordDtoResponse> getAll() {
        return wordService.findAll().stream()
                .map(mapper::wordToWordDtoResponse)
                .toList();
    }

    @GetMapping(path = "/rating/{language}")
    public List<WordDtoResponse> getAllByRating(@PathVariable String language) {
        return wordService.findAllByRatingAndLanguage(language).stream()
                .map(mapper::wordToWordDtoResponse)
                .toList();
    }

    @GetMapping(path = "/list")
    public List<WordDtoResponse> getByIds(@RequestBody GetUserWordsRequest request) {
        return wordService.findListByLang(request.getIds(), request.getLanguage()).stream()
                .map(mapper::wordToWordDtoResponse)
                .peek(wordDtoResponse -> wordDtoResponse.setTranslations(translationService.findAllById(wordDtoResponse.getId())))
                .toList();
    }

    @GetMapping("/search")
    public List<WordInfoDtoResponse> search(@RequestParam("q") String searchText) {
        return wordService.searchWords(searchText).stream()
                .map(word -> WordInfoDtoResponse.builder()
                        .id(word.getId().toString())
                        .word(word.getWord())
                        .language(word.getLanguage())
                        .translations(translationService.findAllByWord(word))
                        .build())
                .toList();
    }

    @GetMapping("/find")
    public Word findOrCreate(@RequestParam("word") String name, @RequestParam("lang") String lang) {
        return wordService.findOrCreateWord(name, lang);
    }

    @GetMapping("/{language}/{stringWord}")
    public WordWithTranslationsDto findByWordAndLang(@PathVariable String language, @PathVariable String stringWord) {
        var word = wordService.findWordByNameAndLang(stringWord, language).orElseThrow(() -> new WordNotFoundException(stringWord, language));
        return WordWithTranslationsDto.builder()
                .id(word.getId())
                .word(word.getWord())
                .language(word.getLanguage())
                .definition(word.getDefinition())
                .antonyms(antonymService.getAllAntonyms(word))
                .synonyms(synonymService.getAllByWord(word))
                .derived(wordFormService.getAllByMainWord(word))
                .mainWord(wordFormService.getMainWord(word))
                .translations(translationService.findAllByWord(word))
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
            for (var word: words) {
                var id = wordService.findOrCreateWord(word, "EN").getId();
                wordService.increaseWeight(id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
