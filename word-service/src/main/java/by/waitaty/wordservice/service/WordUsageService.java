package by.waitaty.wordservice.service;

import by.waitaty.wordservice.model.WordUsage;

import java.util.List;

public interface WordUsageService {
    List<WordUsage> getListByIdsAndLanguage(List<Long> ids, String language);
}
