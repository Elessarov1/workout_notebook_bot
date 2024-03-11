package ru.elessarov.workout_notebook_bot.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.elessarov.workout_notebook_bot.api.entity.Subscription;
import ru.elessarov.workout_notebook_bot.api.entity.UserEntity;
import ru.elessarov.workout_notebook_bot.api.enums.CallBack;
import ru.elessarov.workout_notebook_bot.api.model.CustomMessage;
import ru.elessarov.workout_notebook_bot.api.properties.AdminProperties;
import ru.elessarov.workout_notebook_bot.service.SubscribeService;
import ru.elessarov.workout_notebook_bot.service.UserService;
import ru.elessarov.workout_notebook_bot.utils.TimeUtils;

import java.util.List;

import static ru.elessarov.workout_notebook_bot.api.enums.CallBack.SUBSCRIBE_ONE_MONTH;
import static ru.elessarov.workout_notebook_bot.api.enums.CallBack.SUBSCRIBE_ONE_YEAR;
import static ru.elessarov.workout_notebook_bot.api.enums.CallBack.SUBSCRIBE_THREE_MONTH;
import static ru.elessarov.workout_notebook_bot.utils.BotUtils.*;
import static ru.elessarov.workout_notebook_bot.utils.Constants.CHOOSE_ONE_OF_SUBSCRIPTION;
import static ru.elessarov.workout_notebook_bot.utils.Constants.ONE_MONTH;
import static ru.elessarov.workout_notebook_bot.utils.Constants.SUB_CONFIRM;
import static ru.elessarov.workout_notebook_bot.utils.Constants.THIS_MESSAGE_ONLY_FOR_ADMIN;
import static ru.elessarov.workout_notebook_bot.utils.Constants.THREE_MONTH;
import static ru.elessarov.workout_notebook_bot.utils.Constants.TWELVE_MONTHS;
import static ru.elessarov.workout_notebook_bot.utils.Constants.UNKNOWN_MESSAGE;
import static ru.elessarov.workout_notebook_bot.utils.Constants.YOUR_REQUEST_WAS_SEND;

@Component
@AllArgsConstructor
@Slf4j
public class CallbackHandler {
    private final UserService userService;
    private final SubscribeService subscribeService;
    private final AdminProperties adminProperties;

    public CustomMessage handleCallbacks(Update update) {
        String chatId = getCallbackChatId(update);
        var tgUser = update.getCallbackQuery().getFrom();
        CustomMessage sendMessage = null;
        CallBack callback = CallBack.of(update.getCallbackQuery().getData());
        switch (callback) {
            case SUBSCRIBE -> sendMessage = handleSubscribe(chatId);
            case SUBSCRIBE_ONE_MONTH -> sendMessage = handlePaymentRequest(ONE_MONTH, chatId, tgUser);
            case SUBSCRIBE_THREE_MONTH -> sendMessage = handlePaymentRequest(THREE_MONTH, chatId, tgUser);
            case SUBSCRIBE_ONE_YEAR -> sendMessage = handlePaymentRequest(TWELVE_MONTHS, chatId, tgUser);
            case CONFIRM_PAY -> sendMessage = handleConfirmPay(update);
            case UNKNOWN -> sendMessage = new CustomMessage(new SendMessage(chatId, UNKNOWN_MESSAGE), null);
        }
        return sendMessage;
    }

    private CustomMessage handleConfirmPay(final Update update) {
        SendMessage message;
        String chatId = getCallbackChatId(update);

        if(!isUserAdmin(update.getCallbackQuery().getFrom(), adminProperties.getStringAdminId())) {
            message = new SendMessage(chatId, THIS_MESSAGE_ONLY_FOR_ADMIN);
            return new CustomMessage(message, null);
        }
        int subId = extractSubId(update.getCallbackQuery().getData());
        subscribeService.confirmPay(subId);
        message = new SendMessage(chatId, SUB_CONFIRM);
        return new CustomMessage(message, null);
    }

    private CustomMessage handlePaymentRequest(int months, String chatId, final User tgUser) {
        UserEntity user = userService.findUser(tgUser.getId()).orElseThrow();
        Subscription subscribe = Subscription.builder()
                                             .user(user)
                                             .expireAfter(TimeUtils.getSubscribeTimestamp(months))
                                             .payed(Boolean.FALSE)
                                             .build();
        subscribeService.saveSubscribe(user,subscribe);
        var adminMessage = notifyAdmin(user, months, adminProperties.getStringAdminId());
        var userMessage = new SendMessage(chatId, YOUR_REQUEST_WAS_SEND);
        return new CustomMessage(userMessage, adminMessage);
    }

    private CustomMessage handleSubscribe(String chatId) {
        SendMessage message = new SendMessage(chatId, CHOOSE_ONE_OF_SUBSCRIPTION);
        addKeyboard(message, List.of(SUBSCRIBE_ONE_MONTH.getName(),
                                     SUBSCRIBE_THREE_MONTH.getName(),
                                     SUBSCRIBE_ONE_YEAR.getName())
        );
        return new CustomMessage(message, null);
    }
}
