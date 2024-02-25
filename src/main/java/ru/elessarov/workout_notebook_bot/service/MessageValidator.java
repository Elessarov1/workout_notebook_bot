package ru.elessarov.workout_notebook_bot.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.elessarov.workout_notebook_bot.api.model.TrainingType;
import ru.elessarov.workout_notebook_bot.handler.CallbackHandler;
import ru.elessarov.workout_notebook_bot.handler.CommandHandler;
import ru.elessarov.workout_notebook_bot.handler.JsonHandler;
import ru.elessarov.workout_notebook_bot.utils.Constants;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageValidator {
    private final CommandHandler commandHandler;
    private final CallbackHandler callbackHandler;
    private final JsonHandler jsonHandler;

    public SendMessage validate(Update update) {

        if (isTextMessage(update)) {
            String chatId = update.getMessage().getChatId().toString();
            String text = update.getMessage().getText();

            if (text.startsWith(Constants.SLASH)) {
                return commandHandler.handleCommands(update);

            } else if (text.equalsIgnoreCase(Constants.GREETING)) {
                return getAnswer(chatId, update);

            } else {
                return new SendMessage(chatId, Constants.CANT_UNDERSTAND);
            }
        }
        if (update.hasCallbackQuery()) {
            return callbackHandler.handleCallbacks(update);
        }
        return new SendMessage();
    }

    private boolean isTextMessage(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    private SendMessage getAnswer(String chatId, Update update) {
        String username = update.getMessage().getFrom().getUserName();
        SendMessage sendMessage = new SendMessage(chatId, "Привет %s, выбери вид тренировки".formatted(username));
        addKeyboard(sendMessage, List.of(TrainingType.values()));
        return sendMessage;
    }

    private void addKeyboard(SendMessage sendMessage, List<TrainingType> trainingTypes) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

        for (TrainingType type : trainingTypes) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(type.getDescription());
            //String jsonCallback = jsonHandler.convertToJson(List.of(CallbackType.TRAINING_TYPE.name(), type.getDescription()));
            inlineKeyboardButton.setCallbackData(type.getDescription());
            keyboardButtonsRow.add(inlineKeyboardButton);
        }
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }
}
