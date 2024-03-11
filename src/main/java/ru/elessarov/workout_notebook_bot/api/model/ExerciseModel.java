package ru.elessarov.workout_notebook_bot.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record ExerciseModel(
        @JsonProperty("exercise_id")
        Integer exerciseId,
        @JsonProperty("exercise_name")
        String exerciseName,
        @JsonProperty("muscle_groups")
        List<String> muscleGroups,
        @JsonProperty("description")
        String description,
        @JsonProperty("image_path")
        String imagePath
) {
}
