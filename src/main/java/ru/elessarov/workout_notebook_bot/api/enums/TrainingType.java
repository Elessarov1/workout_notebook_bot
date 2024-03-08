package ru.elessarov.workout_notebook_bot.api.enums;

import java.util.Arrays;

public enum TrainingType {
    CARDIO("Кардио"),
    STRENGTH("Силовая"),
    CROSSFIT("Кроссфит");

    private final String description;

    TrainingType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static TrainingType of(String text) {
        return Arrays.stream(TrainingType.values())
                .filter(value -> value.getDescription().equals(text))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No such type " + text));
    }
}
