package ru.elessarov.workout_notebook_bot.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.elessarov.workout_notebook_bot.api.entity.ExerciseEntity;
import ru.elessarov.workout_notebook_bot.api.model.ExerciseModel;
import ru.elessarov.workout_notebook_bot.converter.ExerciseConverter;
import ru.elessarov.workout_notebook_bot.repository.ExerciseRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final ExerciseConverter exerciseConverter;

    @Transactional
    public ExerciseEntity saveExercise(final ExerciseEntity entity) {
        return exerciseRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<ExerciseModel> getAllExercises() {
        return exerciseRepository.findAll()
                                 .stream()
                                 .map(exerciseConverter::toModel)
                                 .collect(Collectors.toList());
    }
}
