package by.waitaty.learnlanguage.repository;

import by.waitaty.learnlanguage.entity.UserWord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserWordRepository extends JpaRepository<UserWord, Long> {
    @Modifying
    @Query(value = "insert into user_word (id_word, id_user) VALUES (:wordId, :userId)", nativeQuery = true)
    void add(@Param("wordId") Long wordId, @Param("userId") Long userId);

    List<UserWord> findAllByIdUserOrderByIdAsc(Long userId);

    @Query("""
            SELECT uw FROM UserWord uw
            WHERE uw.idUser = :userId\s
            AND (
                (uw.repeatStage = 1)
                OR (uw.repeatStage = 2 AND uw.repeatDate < DATEADD(DAY, -1, CURRENT_DATE()))
                OR (uw.repeatStage = 3 AND uw.repeatDate < DATEADD(DAY, -3, CURRENT_DATE()))
            )
            ORDER BY uw.repeatStage DESC, uw.repeatDate ASC
            """)
    List<UserWord> findAllByIdUserOrderByRepeatStageDescRepeatDateAsc(Long userId, Pageable pageable);

    Optional<UserWord> findByIdWordAndIdUser(Long word, Long userId);

    void deleteByIdWordAndIdUser(Long id, Long userId);

//    Optional<UserWord> findUSerWordByTranslationsContainsAndUser(Translation translation, User user);
}
