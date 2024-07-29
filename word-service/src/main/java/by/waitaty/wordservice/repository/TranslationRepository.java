package by.waitaty.wordservice.repository;

import by.waitaty.wordservice.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {

    List<Translation> findAllByUsageId(Long wordUsageId);
}
