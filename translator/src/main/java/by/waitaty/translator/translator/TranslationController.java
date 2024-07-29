package by.waitaty.translator.translator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    @GetMapping("/translate")
    public String translate(@RequestParam String text,
                            @RequestParam String sourceLang,
                            @RequestParam String targetLang) {
        return translationService.translateText(text, sourceLang, targetLang);
    }
}
