package ru.elessarov.workout_notebook_bot.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
            joinColumns = @JoinColumn(name = EXERCISE_ID),
            inverseJoinColumns = @JoinColumn(name = MuscleGroupEntity.GROUP_ID))
    private List<MuscleGroupEntity> muscleGroups;

    @ManyToMany(mappedBy = "exercises")
    private List<TrainingEntity> trainings;
}
