package by.waitaty.wordservice.repository;

import by.waitaty.wordservice.model.WordUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsageRepository extends JpaRepository<WordUsage, Long> {
    @Query("SELECT wu FROM WordUsage wu " +
           "JOIN FETCH wu.translations t " +
           "WHERE wu.word.id = :wordId AND t.language.id = :languageId")
    List<WordUsage> findAllByWordIdAndLanguageId(@Param("wordId") Long wordId, @Param("languageId") Long languageId);

    List<WordUsage> findByIdInAndWordLanguageCode(List<Long> ids, String language);
}
