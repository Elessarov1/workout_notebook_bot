package ru.elessarov.workout_notebook_bot.api.entity;

import jakarta.persistence.*;
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
    public static final String USERNAME = "username";

    @Id
    @Column(name = USER_ID)
    private Long userId;
    @Column(name = USERNAME)
    private String username;
    @Column(name = "first_name")
    private String firstName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_trainings",
            joinColumns = @JoinColumn(name = USER_ID),
            inverseJoinColumns = @JoinColumn(name = TrainingEntity.TRAINING_ID))
    private List<TrainingEntity> trainings;

    @OneToOne
    @JoinColumn(name = "subscribe_expire_after", referencedColumnName = SubscribeEntity.EXPIRE_AFTER)
    private SubscribeEntity subscribe;

}
