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
    List<Word> findAllByLanguage(String language);

    Optional<Word> findFirstByWordAndLanguage(String word, String language);

    Optional<Word> findByWordAndLanguage(String name, String language);

    List<Word> findAllByWordStartingWith(String searchText);

    Optional<Word> findById(Long id);


    @Query("SELECT w FROM Word w LEFT JOIN WordForm wf ON w.id = wf.derivedWord.id WHERE w.language = :language AND wf.derivedWord IS NULL ORDER BY w.weight DESC")
    List<Word> findAllByLanguageOrderByWeightDesc(@Param("language") String language);

    List<Word> findByIdInAndLanguage(List<Long> ids, String language);
    Word findFirstByIdNotInAndLanguage(List<Long> ids, String language);

    @Modifying
    @Transactional
    @Query("UPDATE Word w SET w.weight = w.weight + 1 WHERE w.id = :id")
    void incrementWeight(Long id);
}
