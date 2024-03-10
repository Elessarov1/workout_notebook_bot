package ru.elessarov.workout_notebook_bot.api.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CallBack {
    SUBSCRIBE("Подписаться"),
    UPDATE_SUBSCRIPTION("Обновить подписку"),
    SUBSCRIBE_ONE_MONTH("1 месяц - 500р"),
    SUBSCRIBE_THREE_MONTH("3 месяца - 1200р"),
    SUBSCRIBE_ONE_YEAR("1 год - 3000р"),
    CONFIRM_PAY("Подтвердить оплату"),
    UNKNOWN("Unknown"),
    CARDIO("Кардио"),
    STRENGTH("Силовая"),
    CROSSFIT("Кроссфит");

    private final String name;

    CallBack(final String name) {
        this.name = name;
    }

    public static CallBack of(String name) {
        return Arrays.stream(CallBack.values())
                .filter(callback -> callback.getName().equals(name))
                .findFirst()
                .orElseGet(() -> checkIfConfirmPay(name));
    }

    private static CallBack checkIfConfirmPay(String name) {
        String[] parts = name.split(",");
        if(parts.length > 1) {
            return CallBack.of(parts[0]);
        }
        return UNKNOWN;
    }
}
