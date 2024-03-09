package ru.elessarov.workout_notebook_bot.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@UtilityClass
public class TimeUtils {
    private static final String MOSCOW = "Europe/Moscow";

    public long getSubscribeTimestamp(int months) {
        return LocalDateTime.now().plusMonths(months).toEpochSecond(getMoscowOffset());
    }

    public ZoneOffset getMoscowOffset() {
        ZoneId zoneId = ZoneId.of(MOSCOW);
        return zoneId.getRules().getOffset(LocalDateTime.now());
    }
}
