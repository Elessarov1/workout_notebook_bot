package ru.elessarov.workout_notebook_bot.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.elessarov.workout_notebook_bot.converter.MuscleGroupConverter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "exercises")
public class ExerciseEntity {
    public static final String EXERCISE_ID = "exercise_id";
    private static final String EXERCISE_NAME = "exercise_name";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exercise_id")
    private Integer exerciseId;

    @Column(name = EXERCISE_NAME, unique = true)
    private String exerciseName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "muscle_groups")
    @Convert(converter = MuscleGroupConverter.class)
    private List<MuscleGroup> muscleGroups;

    @Column(name = "description")
    private String description;

    @Column(name = "image_path")
    private String imagePath;

    @ManyToMany(mappedBy = "exercises")
    private List<TrainingEntity> trainings;

}
