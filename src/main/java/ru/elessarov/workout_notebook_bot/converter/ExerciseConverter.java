package ru.elessarov.workout_notebook_bot.converter;

import org.springframework.stereotype.Component;
import ru.elessarov.workout_notebook_bot.api.entity.ExerciseEntity;
import ru.elessarov.workout_notebook_bot.api.entity.MuscleGroup;
import ru.elessarov.workout_notebook_bot.api.model.ExerciseModel;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class ExerciseConverter {

    public ExerciseEntity toEntity(final ExerciseModel model) {
        return ExerciseEntity.builder()
                             .exerciseName(model.exerciseName())
                             .description(model.description())
                             .imagePath(model.imagePath())
                             .muscleGroups(model.muscleGroups().stream().map(MuscleGroup::of).collect(Collectors.toList()))
                             .trainings(new ArrayList<>())
                             .build();
    }

    public ExerciseModel toModel(final ExerciseEntity entity) {
        return ExerciseModel.builder()
                            .exerciseName(entity.getExerciseName())
                            .description(entity.getDescription())
                            .muscleGroups(entity.getMuscleGroups().stream().map(MuscleGroup::getName).collect(Collectors.toList()))
                            .imagePath(entity.getImagePath())
                            .build();
    }
}
