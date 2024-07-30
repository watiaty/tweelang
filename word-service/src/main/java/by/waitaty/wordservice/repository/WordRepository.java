package by.waitaty.wordservice.repository;

import by.waitaty.wordservice.model.Language;
import by.waitaty.wordservice.model.Word;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findAllByTextStartingWith(String searchText);

    Optional<Word> findFirstByTextAndLanguage(String word, Language language);

    Optional<Word> findByTextAndLanguageCode(String name, String languageCode);

    @Query("SELECT w FROM Word w INNER JOIN Language l ON w.language.id = l.id WHERE w.language.code = :languageCode ORDER BY w.weight DESC")
    List<Word> findAllByLanguageOrderByWeightDesc(@Param("languageCode") String languageCode);

    Word findFirstByIdNotInAndLanguageName(List<Long> ids, String language);

    @Modifying
    @Transactional
    @Query("UPDATE Word w SET w.weight = w.weight + 1 WHERE w.id = :id")
    void incrementWeight(Long id);
}
