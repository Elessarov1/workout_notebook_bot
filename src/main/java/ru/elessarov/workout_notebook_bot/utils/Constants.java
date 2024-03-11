package ru.elessarov.workout_notebook_bot.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final int ONE_MONTH = 1;
    public static final int THREE_MONTH = 3;
    public static final int TWELVE_MONTHS = 12;
    public static final String CANT_UNDERSTAND = "Извините, бот пока не умеет работать с этой командой";
    public static final String SUBSCRIBE_EXPIRES = "Ваша подписка истекла, пожалуйста обновите ее";
    public static final String SUBSCRIBE_ACTUAL_UNTIL = "Ваша подписка действительна до ";
    public static final String YOU_DONT_HAVE_SUBSCRIPTION = "У вас пока нет действующей подписки";
    public static final String GREETING_TEXT = "Привет %s,\n" + "если нужна помощь с командами воспользуйся командой - %s";
    public static final String CHOOSE_TRAINING_TYPE = "Выбери вид тренировки";
    public static final String YOUR_REQUEST_WAS_SEND = "Ваша заявка принята, администратор свяжется с вами в ближайшее время";
    public static final String CHOOSE_ONE_OF_SUBSCRIPTION = "Выберите один из вариантов подписки";
    public static final String THIS_MESSAGE_ONLY_FOR_ADMIN = "Это сообщение доступно только админу";
    public static final String SUB_CONFIRM = "Подписка подтверждена";
    public static final String UNKNOWN_MESSAGE = "Unknown message";

    public static final String HELP_BLOCK = """
            %s - %s\s
            %s - %s\s
            %s - %s\s
             %s - %s""";
}
