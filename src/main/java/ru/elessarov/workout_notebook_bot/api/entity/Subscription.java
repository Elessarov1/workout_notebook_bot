package ru.elessarov.workout_notebook_bot.api.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = Subscription.SUBSCRIPTION)
public class Subscription {
    public static final String EXPIRE_AFTER = "expire_after";
    public static final String SUBSCRIPTION = "subscription";
    public static final String SUB_ID = "sub_id";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = SUB_ID)
    private Integer id;
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

    public Subscription confirmPay() {
        this.payed = true;
        return this;
    }
}
