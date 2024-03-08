package ru.elessarov.workout_notebook_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.elessarov.workout_notebook_bot.api.entity.SubscribeEntity;

public interface SubscribeRepository extends JpaRepository<SubscribeEntity, Integer> {

}
