package ru.elessarov.workout_notebook_bot.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.elessarov.workout_notebook_bot.api.config.BotProperties;
import ru.elessarov.workout_notebook_bot.api.enums.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
@AllArgsConstructor
@Slf4j
public class WorkoutBot extends TelegramLongPollingBot {
    private final BotProperties botProperties;
    private final MessageValidator validator;

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
        sendMessage(validator.validate(update));
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @PostConstruct
    private void setCommands() throws TelegramApiException {
        List<BotCommand> commands = new ArrayList<>();
        Arrays.stream(Command.values())
                .filter(command -> command.getMenuExcluded().equals(Boolean.FALSE))
                .forEach(command -> commands.add(new BotCommand(command.getName(), command.getDescription())));
        SetMyCommands setMyCommands = new SetMyCommands(commands, null, null);
        this.execute(setMyCommands);
    }
}
