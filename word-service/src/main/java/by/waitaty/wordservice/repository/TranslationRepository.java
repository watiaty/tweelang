package by.waitaty.wordservice.repository;

import by.waitaty.wordservice.dto.TranslationDto;
import by.waitaty.wordservice.model.Translation;
import by.waitaty.wordservice.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {
    Optional<Translation> findByTranslatedWordAndWord(Word translation, Word word);

    @Query("SELECT new by.waitaty.wordservice.dto.TranslationDto(t.id, t.translatedWord.word) FROM Translation t where t.word = :word")
    List<TranslationDto> findAllByWord(Word word);

    @Query("SELECT new by.waitaty.wordservice.dto.TranslationDto(t.id, t.translatedWord.word) FROM Translation t where t.word.id = :id")
    List<TranslationDto> findAllById(Long id);
}