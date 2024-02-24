package ru.elessarov.workout_notebook_bot.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
@Slf4j
public class CallbackHandler {
    public SendMessage handleCallbacks(Update update) {
        long id = update.getMessage().getChatId();
        return new SendMessage(String.valueOf(id), "callback message");
    }
}
