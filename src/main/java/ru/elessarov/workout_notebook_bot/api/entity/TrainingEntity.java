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
import ru.elessarov.workout_notebook_bot.api.model.TrainingType;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "trainings")
public class TrainingEntity {

    public static final String TRAINING_ID = "training_id";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = TRAINING_ID)
    private Integer trainingId;

    @Column(name = "training_name", unique = true)
    private String trainingName;

    @Column(name = "training_type")
    @Enumerated(EnumType.STRING)
    private TrainingType trainingType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "trainings_exercise",
            joinColumns = @JoinColumn(name = ExerciseEntity.EXERCISE_ID),
            inverseJoinColumns = @JoinColumn(name = TRAINING_ID))
    private List<ExerciseEntity> exercises;

    @ManyToMany(mappedBy = "trainings")
    private List<UserEntity> users;
}
