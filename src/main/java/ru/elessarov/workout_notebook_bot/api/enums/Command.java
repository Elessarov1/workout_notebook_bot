package ru.elessarov.workout_notebook_bot.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Command {
    START("/start", "стартовое приветствие", true),
    HELP("/help", "выводит весь список команд"),
    TRAININGS("/trainings", "выбрать одну из стандартных тренировок"),
    MY_TRAININGS("/my_trainings", "показать сохраненные тренировки"),
    SUBSCRIPTION("/subscription", "показать информацию о подписке"),
    EXERCISES("/exercises", "показать список тренировок"),
    UNKNOWN("/unknown", "неизвестное сообщение", true);

     private final String name;
     private final String description;
     private final Boolean menuExcluded;

    Command(String name, String description) {
        this.name = name;
        this.description = description;
        this.menuExcluded = false;
    }

    public static Command of(String text) {
        return Arrays.stream(Command.values())
                .filter(command -> command.getName().equals(text))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
