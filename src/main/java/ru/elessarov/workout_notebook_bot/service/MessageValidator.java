package ru.elessarov.workout_notebook_bot.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.elessarov.workout_notebook_bot.handler.CallbackHandler;
import ru.elessarov.workout_notebook_bot.handler.CommandHandler;


@Service
@AllArgsConstructor
public class MessageValidator {
    private final CommandHandler commandHandler;
    private final CallbackHandler callbackHandler;

    public SendMessage validate(Update update) {
        if (isTextMessage(update)) {
            return commandHandler.handleCommands(update);
        }
        if (update.hasCallbackQuery()) {
            return callbackHandler.handleCallbacks(update);
        }
        return commandHandler.sendUnknownMessage(update);
    }

    private boolean isTextMessage(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }
}
