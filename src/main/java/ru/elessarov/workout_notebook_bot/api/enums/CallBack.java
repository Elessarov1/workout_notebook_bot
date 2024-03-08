package ru.elessarov.workout_notebook_bot.api.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CallBack {
    SUBSCRIBE("Подписаться"),
    UPDATE_SUBSCRIPTION("Обновить подписку");

    private final String name;

    CallBack(final String name) {
        this.name = name;
    }

    public static CallBack of(String name) {
        return Arrays.stream(CallBack.values())
                .filter(callback -> callback.getName().equals(name))
                .findFirst()
                .orElseThrow();
    }
}
