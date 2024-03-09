package ru.elessarov.workout_notebook_bot.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.elessarov.workout_notebook_bot.api.entity.Subscription;
import ru.elessarov.workout_notebook_bot.api.entity.UserEntity;
import ru.elessarov.workout_notebook_bot.api.enums.CallBack;
import ru.elessarov.workout_notebook_bot.repository.SubscribeRepository;
import ru.elessarov.workout_notebook_bot.utils.TimeUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ru.elessarov.workout_notebook_bot.utils.BotUtils.*;
import static ru.elessarov.workout_notebook_bot.utils.Constants.*;

@Service
@AllArgsConstructor
public class SubscribeService {
    private final UserService userService;
    private final SubscribeRepository subscribeRepository;

    public SendMessage checkUserSubscribe(Update update) {
        var tgUser = update.getMessage().getFrom();
        if (!userService.isUserExists(tgUser.getId())) {
            userService.saveUser(tgUser, getChatId(update));
        }
        long userId = tgUser.getId();
        try {
            long timestamp = userService.findUser(userId)
                    .map(UserEntity::getSubscribe)
                    .map(Subscription::getExpireAfter)
                    .orElseThrow();

            if (isSubscribeExpires(timestamp)) {
                SendMessage message = new SendMessage(getChatId(update), SUBSCRIBE_EXPIRES);
                addKeyboard(message, List.of(CallBack.UPDATE_SUBSCRIPTION.getName()));
                return message;
            }
            var time = LocalDateTime.ofEpochSecond(timestamp, 0, TimeUtils.getMoscowOffset());
            return new SendMessage(getChatId(update), SUBSCRIBE_ACTUAL_UNTIL + formatDate(time.toString()));

        } catch (Exception e) {
            var message = new SendMessage(getChatId(update), YOU_DONT_HAVE_SUBSCRIPTION);
            addKeyboard(message, List.of(CallBack.SUBSCRIBE.getName()));
            return message;
        }
    }

    private boolean isSubscribeExpires(final long timestamp) {
        long now = Instant.now().getEpochSecond();
        return now > timestamp;
    }

    @Transactional
    public Subscription saveSubscribe(final UserEntity user, final Subscription entity) {
        var sub =  subscribeRepository.save(entity);
        user.setSubscribe(sub);
        userService.saveUser(user);
        return sub;
    }

    public Subscription confirmPay(int subId) {
        return findSubscription(subId)
                .map(Subscription::confirmPay)
                .map(subscribeRepository::save)
                .orElseThrow();
    }

    public Optional<Subscription> findSubscription(int subId) {
        return subscribeRepository.findById(subId);
    }
}
