package by.waitaty.wordservice.repository;

import by.waitaty.wordservice.model.WordUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsageRepository extends JpaRepository<WordUsage, Long> {
    List<WordUsage> findByIdInAndWordLanguageCode(List<Long> ids, String language);
}
