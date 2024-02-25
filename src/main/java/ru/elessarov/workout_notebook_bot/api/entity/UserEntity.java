package ru.elessarov.workout_notebook_bot.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

import static ru.elessarov.workout_notebook_bot.api.entity.ExerciseEntity.EXERCISE_ID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class UserEntity {
    private static final String USER_ID = "user_id";

    @Id
    @Column(name = USER_ID)
    private Integer userId;
    @Column
    private String username;
    @Column(name = "first_name")
    private String firstName;
    @ManyToMany
    @JoinTable(name = "user_exercises",
            joinColumns = @JoinColumn(name = USER_ID),
            inverseJoinColumns = @JoinColumn(name = EXERCISE_ID))
    @MapKey(name = "exerciseName")
    private Map<String, ExerciseEntity> exercises;
}
