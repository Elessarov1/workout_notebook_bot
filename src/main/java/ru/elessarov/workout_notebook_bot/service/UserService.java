package ru.elessarov.workout_notebook_bot.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
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

    @Transactional
    public void saveUser(Update update) {
        var tgUser = update.getMessage().getFrom();
        UserEntity user = UserEntity.builder()
                .userId(tgUser.getId())
                .username(tgUser.getUserName())
                .firstName(tgUser.getFirstName())
                .trainings(new ArrayList<>())
                .build();
        userRepository.save(user);
    }
}
