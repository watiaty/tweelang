package by.waitaty.wordservice.repository;

import by.waitaty.wordservice.model.Antonym;
import by.waitaty.wordservice.model.Synonym;
import by.waitaty.wordservice.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SynonymRepository extends JpaRepository<Synonym, Long> {
    List<Synonym> findAllByWord(Word word);

    List<Synonym> findAllBySynonymWord(Word word);
}
