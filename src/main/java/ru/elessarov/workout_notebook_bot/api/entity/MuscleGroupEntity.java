package ru.elessarov.workout_notebook_bot.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @ManyToMany(mappedBy = "muscleGroups")
    private List<ExerciseEntity> exercises;
}
