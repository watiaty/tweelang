package by.waitaty.wordservice.service;

import by.waitaty.wordservice.model.Language;

public interface LanguageService {
    Language getByCode(String languageCode);
}
