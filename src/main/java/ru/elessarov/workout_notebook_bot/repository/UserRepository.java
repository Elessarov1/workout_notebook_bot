package ru.elessarov.workout_notebook_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.elessarov.workout_notebook_bot.api.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
