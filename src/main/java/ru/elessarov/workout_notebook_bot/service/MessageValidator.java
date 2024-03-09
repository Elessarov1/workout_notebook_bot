package ru.elessarov.workout_notebook_bot.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.elessarov.workout_notebook_bot.api.model.CustomMessage;
import ru.elessarov.workout_notebook_bot.handler.CallbackHandler;
import ru.elessarov.workout_notebook_bot.handler.CommandHandler;


@Service
@AllArgsConstructor
public class MessageValidator {
    private final CommandHandler commandHandler;
    private final CallbackHandler callbackHandler;

    public CustomMessage validate(Update update) {
        if (isTextMessage(update)) {
            return new CustomMessage(commandHandler.handleCommands(update), null);
        }
        if (update.hasCallbackQuery()) {
            return callbackHandler.handleCallbacks(update);
        }
        return new CustomMessage(commandHandler.sendUnknownMessage(update), null);
    }

    private boolean isTextMessage(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }
}
