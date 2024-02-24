package ru.elessarov.workout_notebook_bot;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.elessarov.workout_notebook_bot.config.BotProperties;
import ru.elessarov.workout_notebook_bot.handler.CallbackHandler;
import ru.elessarov.workout_notebook_bot.handler.CommandHandler;
import ru.elessarov.workout_notebook_bot.utils.Constants;

@Component
@AllArgsConstructor
@Slf4j
public class WorkoutBot extends TelegramLongPollingBot {
    private final BotProperties botProperties;
    private final CommandHandler commandHandler;
    private final CallbackHandler callbackHandler;
    @Override
    public String getBotUsername() {
        return botProperties.getName();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (isTextMessage(update)) {
            Message message = update.getMessage();
            String chatId = update.getMessage().getChatId().toString();

            if (message.getText().startsWith(Constants.SLASH)) {
                sendMessage(commandHandler.handleCommands(update));
            } else {
                sendMessage(new SendMessage(chatId, Constants.CANT_UNDERSTAND));
            }
        } else if (update.hasCallbackQuery()) {
            sendMessage(callbackHandler.handleCallbacks(update));
        }
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private boolean isTextMessage(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }
}
