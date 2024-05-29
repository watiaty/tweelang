package by.waitaty.wordservice.repository;

import by.waitaty.wordservice.model.Antonym;
import by.waitaty.wordservice.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AntonymRepository extends JpaRepository<Antonym, Long> {
    List<Antonym> findAllByWord(Word word);

    List<Antonym> findAllByAntonymWord(Word word);
}
