package ru.elessarov.workout_notebook_bot.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import ru.elessarov.workout_notebook_bot.api.model.TrainingType;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "exercise")
public class ExerciseEntity {
    public static final String EXERCISE_ID = "exercise_id";
    private static final String EXERCISE_NAME = "exercise_name";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exercise_id")
    private Integer exerciseId;
    @Column(name = EXERCISE_NAME, unique = true)
    private String exerciseName;
    @Enumerated(EnumType.STRING)
    @Column(name = "training_type")
    private TrainingType trainingType;
    @BatchSize(size = 50)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "exercise_muscle_groups",
            joinColumns = @JoinColumn(name = MuscleGroupEntity.GROUP_ID),
            inverseJoinColumns = @JoinColumn(name = EXERCISE_ID))
    private List<MuscleGroupEntity> muscleGroups;
}
