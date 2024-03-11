package ru.elessarov.workout_notebook_bot.utils;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.elessarov.workout_notebook_bot.api.entity.UserEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static ru.elessarov.workout_notebook_bot.utils.ExternalKey.ADMIN_ID;

@UtilityClass
public class BotUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String getChatId(Update update) {
        return update.getMessage().getChatId().toString();
    }

    public static String getCallbackChatId(Update update) {
        return update.getCallbackQuery().getMessage().getChatId().toString();
    }

    public static void addKeyboard(SendMessage sendMessage, List<String> collection) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

        for (String item : collection) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(item);
            inlineKeyboardButton.setCallbackData(item);
            keyboardButtonsRow.add(inlineKeyboardButton);
        }
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }

    public static SendMessage notifyAdmin(final UserEntity user, final int months) {
        var message = new SendMessage(ADMIN_ID, "@%s want to sub for %d months".formatted(user.getUsername(), months));
        addKeyboard(message, List.of("Подтвердить оплату, %d".formatted(user.getSubscribe().getId())));
        return message;
    }

    public static boolean isUserAdmin(final User user) {
        return String.valueOf(user.getId()).equals(ADMIN_ID);
    }

    public static int extractSubId(final String value) {
        String[] parts = value.split(",");
        if (parts.length > 0) {
            return Integer.parseInt(parts[1].trim());
        }
        return -1;
    }

    public static String formatDate(String dateTimeString) {
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        return dateTime.format(dateFormatter);
    }
}
