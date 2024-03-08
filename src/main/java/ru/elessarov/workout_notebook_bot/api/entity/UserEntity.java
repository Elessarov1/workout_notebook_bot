package ru.elessarov.workout_notebook_bot.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

import java.util.List;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_trainings",
            joinColumns = @JoinColumn(name = USER_ID),
            inverseJoinColumns = @JoinColumn(name = TrainingEntity.TRAINING_ID))
    private List<TrainingEntity> trainings;
}
