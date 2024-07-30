package by.waitaty.wordservice.service.impl;

import by.waitaty.wordservice.exception.LanguageNotFoundException;
import by.waitaty.wordservice.model.Language;
import by.waitaty.wordservice.repository.LanguageRepository;
import by.waitaty.wordservice.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {
    private final LanguageRepository languageRepository;

    public Language getByCode(String languageCode) {
        return languageRepository.findByCode(languageCode).orElseThrow(() -> new LanguageNotFoundException(languageCode));
    }
}
