package by.waitaty.learnlanguage.controller;

import by.waitaty.learnlanguage.entity.Language;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/language")
@AllArgsConstructor
public class LanguageController {
    @GetMapping()
    public List<String> getAll() {
        return Arrays.stream(Language.values()).map(Language::getId).toList();
    }
}
