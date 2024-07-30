package by.waitaty.wordservice.repository;

import by.waitaty.wordservice.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    Optional<Language> findByCode(String languageCode);
}
