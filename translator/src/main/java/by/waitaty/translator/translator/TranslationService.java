package by.waitaty.translator.translator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class TranslationService {

    @Value("${libretranslate.api.url}")
    private String apiUrl;

    public String translateText(String text, String sourceLang, String targetLang) {
        RestTemplate restTemplate = new RestTemplate();
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("q", "{text}")
                .queryParam("source", "{sourceLang}")
                .queryParam("target", "{targetLang}")
                .queryParam("format", "text")
                .encode()
                .toUriString();

        Map<String, String> params = new HashMap<>();
        params.put("text", text);
        params.put("sourceLang", sourceLang);
        params.put("targetLang", targetLang);

        return restTemplate.getForObject(urlTemplate, String.class, params);
    }
}
