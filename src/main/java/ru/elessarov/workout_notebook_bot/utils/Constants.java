package ru.elessarov.workout_notebook_bot.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public final String CANT_UNDERSTAND = "Извините, бот пока не умеет работать с этой командой";
    public final String SUBSCRIBE_EXPIRES = "Ваша подписка истекла, пожалуйста обновите ее";
    public final String SUBSCRIBE_ACTUAL_UNTIL = "Ваша подписка действительна до";
    public final String YOU_DONT_HAVE_SUBSCRIBTION = "У вас пока нет действующей подписки";
    public final String GREETING_TEXT = "Привет %s,\n" + "если нужна помощь с командами воспользуйся командой - %s";
    public final String CHOOSE_TRAINING_TYPE = "Выбери вид тренировки";
    public static final String HELP_BLOCK = """
                        %s - %s\s
                        %s - %s\s
                        %s - %s\s
                         %s - %s""";
}
