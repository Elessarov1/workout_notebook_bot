package ru.elessarov.workout_notebook_bot.api.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "muscle_group")
public class MuscleGroupEntity {
    public static final String GROUP_NAME = "group_name";
    public static final String GROUP_ID = "group_id";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = GROUP_ID)
    private Integer groupId;
    @Column(name = GROUP_NAME, unique = true)
    private String groupName;
}
