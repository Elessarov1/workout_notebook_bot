package ru.elessarov.workout_notebook_bot.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.elessarov.workout_notebook_bot.api.entity.UserEntity;
import ru.elessarov.workout_notebook_bot.repository.UserRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<UserEntity> findUser(final long userId) {
        return userRepository.findById(userId);
    }

    public boolean isUserExists(long userId) {
        return userRepository.existsById(userId);
    }

    @Transactional
    public void saveUser(User tgUser, String chatId) {
        UserEntity user = UserEntity.builder()
                .userId(tgUser.getId())
                .username(tgUser.getUserName())
                .firstName(tgUser.getFirstName())
                .chatId(chatId)
                .trainings(new ArrayList<>())
                .build();
        userRepository.save(user);
    }

    @Transactional
    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }
}
