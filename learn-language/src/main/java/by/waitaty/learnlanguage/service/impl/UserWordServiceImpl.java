package by.waitaty.learnlanguage.service.impl;

import by.waitaty.learnlanguage.entity.UserWord;
import by.waitaty.learnlanguage.repository.UserWordRepository;
import by.waitaty.learnlanguage.service.UserWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserWordServiceImpl implements UserWordService {
    private final UserWordRepository userWordRepository;

    public void update(UserWord userWord) {
        userWordRepository.save(userWord);
    }

    public void deleteByIdAndUserId(Long id, Long userId) {
        userWordRepository.deleteByIdWordAndIdUser(id, userId);
    }

    public List<UserWord> getAllByUserId(Long userId) {
        return userWordRepository.findAllByIdUserOrderByIdAsc(userId);
    }

    public List<UserWord> getQuantityLearningWordsByUserId(int quantity, Long userId) {
        if (quantity == 0) quantity = Integer.MAX_VALUE;
        Pageable pageable = PageRequest.of(0, quantity);
        return userWordRepository.findAllByIdUserOrderByRepeatStageDescRepeatDateAsc(userId, pageable);
    }

    public Optional<UserWord> getByWordIdAndUserId(Long wordId, Long userId) {
        return userWordRepository.findByIdWordAndIdUser(wordId, userId);
    }

    public boolean existsByUserIdAndWordId(Long userId, Long wordId) {
        return userWordRepository.existsByIdUserAndIdWord(userId, wordId);
    }
}
