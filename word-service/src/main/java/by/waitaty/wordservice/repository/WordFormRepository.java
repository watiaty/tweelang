package by.waitaty.wordservice.repository;

import by.waitaty.wordservice.model.Word;
import by.waitaty.wordservice.model.WordForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WordFormRepository extends JpaRepository<WordForm, Long> {

    List<WordForm> findAllByMainWord(Word word);

    Optional<WordForm> findByDerivedWord(Word word);
}
