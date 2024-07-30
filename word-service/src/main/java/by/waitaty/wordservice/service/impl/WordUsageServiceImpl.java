package by.waitaty.wordservice.service.impl;

import by.waitaty.wordservice.model.WordUsage;
import by.waitaty.wordservice.repository.UsageRepository;
import by.waitaty.wordservice.service.WordUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordUsageServiceImpl implements WordUsageService {
    private final UsageRepository usageRepository;

    public List<WordUsage> getListByIdsAndLanguage(List<Long> ids, String language) {
        return usageRepository.findByIdInAndWordLanguageCode(ids, language);
    }
}
