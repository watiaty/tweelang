package by.waitaty.learnlanguage.service;

import by.waitaty.learnlanguage.entity.UserWord;

import java.util.List;
import java.util.Optional;

public interface UserWordService {
    void save(UserWord userWord);

    void deleteByIdAndUserId(Long id, Long userId);

    List<UserWord> getAllByUserId(Long userId);

    List<UserWord> getQuantityLearningWordsByUserId(int quantity, Long userId);

    Optional<UserWord> getByWordIdAndUserId(Long wordId, Long userId);

    boolean existsByUserIdAndWordId(Long userId, Long wordId);
}
