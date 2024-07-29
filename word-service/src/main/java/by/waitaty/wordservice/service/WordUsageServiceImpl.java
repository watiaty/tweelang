package by.waitaty.wordservice.service;

import by.waitaty.wordservice.model.WordUsage;
import by.waitaty.wordservice.repository.UsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordUsageServiceImpl implements WordUsageService {
    private final UsageRepository usageRepository;

    public List<WordUsage> findListByIdsAndLanguage(List<Long> ids, String language) {
        return usageRepository.findByIdInAndWordLanguageCode(ids, language);
    }
}
