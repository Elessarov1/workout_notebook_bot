package ru.elessarov.workout_notebook_bot.api.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = SubscribeEntity.SUBSCRIBE)
public class SubscribeEntity {
    public static final String EXPIRE_AFTER = "expire_after";
    public static final String SUBSCRIBE = "subscribe";

    @Id
    private String secret;
    @Column(name = EXPIRE_AFTER)
    private Long expireAfter;
    @Column
    private Boolean payed;

    @OneToOne
    @JoinColumn(name = UserEntity.USERNAME, referencedColumnName = UserEntity.USERNAME)
    private UserEntity user;

    public boolean isPayed() {
        return payed;
    }

    public boolean confirmPay() {
        return this.payed = true;
    }
}
