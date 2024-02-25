package ru.elessarov.workout_notebook_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.elessarov.workout_notebook_bot.api.entity.MuscleGroupEntity;

public interface MuscleGroupRepository extends JpaRepository<MuscleGroupEntity, Integer> {
    MuscleGroupEntity getByGroupName(final String name);
}
