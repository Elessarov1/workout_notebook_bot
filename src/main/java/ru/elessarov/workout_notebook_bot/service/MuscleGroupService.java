package ru.elessarov.workout_notebook_bot.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.elessarov.workout_notebook_bot.api.entity.MuscleGroupEntity;
import ru.elessarov.workout_notebook_bot.repository.MuscleGroupRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class MuscleGroupService {
    private final MuscleGroupRepository repository;

    public List<String> getMuscleGroupNames() {
        return repository.findAll()
                .stream()
                .map(MuscleGroupEntity::getGroupName)
                .toList();
    }

    public MuscleGroupEntity getByName(final String name) {
        return repository.getByGroupName(name);
    }
}
