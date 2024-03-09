package ru.elessarov.workout_notebook_bot.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.elessarov.workout_notebook_bot.api.entity.SubscribeEntity;
import ru.elessarov.workout_notebook_bot.api.entity.UserEntity;
import ru.elessarov.workout_notebook_bot.api.enums.CallBack;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import static ru.elessarov.workout_notebook_bot.utils.BotUtils.*;
import static ru.elessarov.workout_notebook_bot.utils.Constants.*;

@Service
@AllArgsConstructor
public class SubscribeService {
    private final UserService userService;

    public SendMessage checkUserSubscribe(Update update) {
        userService.saveUser(update);
        long userId = update.getMessage().getFrom().getId();
        try {
            long timestamp = userService.findUser(userId)
                    .map(UserEntity::getSubscribe)
                    .map(SubscribeEntity::getExpireAfter)
                    .orElseThrow();

            if (isSubscribeExpires(timestamp)) {
                SendMessage message = new SendMessage(getChatId(update), SUBSCRIBE_EXPIRES);
                addKeyboard(message, List.of(CallBack.UPDATE_SUBSCRIPTION.getName()));
                return message;
            }
            ZoneId zoneId = ZoneId.of("Europe/Moscow");
            ZoneOffset zoneOffset = zoneId.getRules().getOffset(LocalDateTime.now());
            var time = LocalDateTime.ofEpochSecond(timestamp, 0, zoneOffset);
            return new SendMessage(getChatId(update), SUBSCRIBE_ACTUAL_UNTIL + time);

        } catch (Exception e) {
            var message = new SendMessage(getChatId(update), YOU_DONT_HAVE_SUBSCRIBTION);
            addKeyboard(message, List.of(CallBack.SUBSCRIBE.getName()));
            return message;
        }
    }

    private boolean isSubscribeExpires(final long timestamp) {
        long now = Instant.now().getEpochSecond();
        return now > timestamp;
    }
}
