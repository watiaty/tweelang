package by.waitaty.wordservice.repository;

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
    Optional<Word> findFirstByTextAndLanguage_Name(String word, String language);

    Optional<Word> findByTextAndLanguage_Code(String name, String languageCode);

    List<Word> findAllByTextStartingWith(@Param("searchText") String searchText);

    @Query("SELECT w FROM Word w INNER JOIN Language l ON w.language.id = l.id WHERE w.language.code = :languageCode ORDER BY w.weight DESC")
    List<Word> findAllByLanguageOrderByWeightDesc(@Param("languageCode") String languageCode);

    List<Word> findByIdInAndLanguage_Name(List<Long> ids, String language);

    Word findFirstByIdNotInAndLanguage_Name(List<Long> ids, String language);

    @Modifying
    @Transactional
    @Query("UPDATE Word w SET w.weight = w.weight + 1 WHERE w.id = :id")
    void incrementWeight(Long id);
}
