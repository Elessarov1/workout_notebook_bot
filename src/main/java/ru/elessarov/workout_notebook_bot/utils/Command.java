package ru.elessarov.workout_notebook_bot.utils;

import java.util.Arrays;

public enum Command {
     START("/start"),
     HELP("/help"),
     TRAININGS("/trainings"),
     MY_TRAININGS("/my_trainings"),
     SUBSCRIPTION("/subscription");

     private final String text;

    Command(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static Command of(String text) {
        return Arrays.stream(Command.values())
                .filter(command -> command.getText().equals(text))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No such command " + text));
    }
}
