package ru.elessarov.workout_notebook_bot.api.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MuscleGroup {
    CHEST("Грудь"),
    BACK("Спина"),
    BICEPS("Бицепс"),
    TRICEPS("Трицепс"),
    DELTS("Дельты"),
    LEGS("Ноги");

    private final String name;

    MuscleGroup(String name) {
        this.name = name;
    }

    public static MuscleGroup of(String text) {
        return Arrays.stream(MuscleGroup.values())
                .filter(group -> group.getName().equals(text))
                .findFirst()
                .orElseThrow();
    }
}
