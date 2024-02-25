package ru.elessarov.workout_notebook_bot.api.model;

public enum TrainingType {
    CARDIO("Кардио"),
    STRENGTH("Силовая"),
    STRETCH("Растяжка"),
    CROSSFIT("Кроссфит");

    private final String description;

    TrainingType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
