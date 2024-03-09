package ru.elessarov.workout_notebook_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.elessarov.workout_notebook_bot.api.entity.Subscription;

public interface SubscribeRepository extends JpaRepository<Subscription, Integer> {

}
