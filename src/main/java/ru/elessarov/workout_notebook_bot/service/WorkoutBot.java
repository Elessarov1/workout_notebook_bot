package ru.elessarov.workout_notebook_bot.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.elessarov.workout_notebook_bot.api.config.BotProperties;
import ru.elessarov.workout_notebook_bot.api.enums.Command;
import ru.elessarov.workout_notebook_bot.api.model.CustomMessage;
import ru.elessarov.workout_notebook_bot.handler.CallbackHandler;
import ru.elessarov.workout_notebook_bot.handler.CommandHandler;
import ru.elessarov.workout_notebook_bot.handler.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
@AllArgsConstructor
@Slf4j
public class WorkoutBot extends TelegramLongPollingBot {
    private final BotProperties botProperties;
    private final CommandHandler commandHandler;
    private final CallbackHandler callbackHandler;
    private final FileHandler fileHandler;


    private void sendMessage(CustomMessage sendMessage) {
        try {
            if (sendMessage.hasAdminMessage()) {
                execute(sendMessage.adminMessage());
            }
            execute(sendMessage.message());
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    public CustomMessage validate(Update update) {
        if (update.getMessage().hasDocument()) {
            var file = convertDocumentToFile(update.getMessage().getDocument());
            return fileHandler.updateData(update, file);
        }
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

    private File convertDocumentToFile(Document document) {
        String fileId = document.getFileId();
        org.telegram.telegrambots.meta.api.objects.File file;
        File tempFile;
        try {
            file = execute(GetFile.builder().fileId(fileId).build());
            tempFile = File.createTempFile("file", null);
            downloadFile(file.getFilePath(), tempFile);
        } catch (IOException | TelegramApiException e) {
            throw new RuntimeException(e);
        }

        return tempFile;
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
        sendMessage(validate(update));
    }
}
