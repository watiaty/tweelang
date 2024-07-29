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

    public List<UserWord> getAllByUserId(Long userId) {
        return userWordRepository.findAllByIdUserOrderByIdAsc(userId);
    }

    public List<UserWord> getCountLearningWords(int count, Long userId) {
        if (count == 0) count = Integer.MAX_VALUE;
        Pageable pageable = PageRequest.of(0, count);
        return userWordRepository.findAllByIdUserOrderByRepeatStageDescRepeatDateAsc(userId, pageable);
    }

    public Optional<UserWord> getUserWordByWord(Long id, Long userId) {
        return userWordRepository.findByIdWordAndIdUser(id, userId);
    }

    public UserWord update(UserWord userWord) {
        return userWordRepository.save(userWord);
    }

    public void delete(Long id, Long userId) {
        userWordRepository.deleteByIdWordAndIdUser(id, userId);
    }

    public Optional<UserWord> findByIdWordAndIdUser(Long wordId, Long userId) {
        return userWordRepository.findByIdWordAndIdUser(wordId, userId);
    }

    public boolean existsByIdUserAndIdWord(Long userId, Long wordId) {
        return userWordRepository.existsByIdUserAndIdWord(userId, wordId);
    }
}
