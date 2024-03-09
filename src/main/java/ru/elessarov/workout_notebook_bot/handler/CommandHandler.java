package ru.elessarov.workout_notebook_bot.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.elessarov.workout_notebook_bot.api.enums.TrainingType;
import ru.elessarov.workout_notebook_bot.api.enums.Command;
import ru.elessarov.workout_notebook_bot.service.SubscribeService;
import ru.elessarov.workout_notebook_bot.utils.BotUtils;
import ru.elessarov.workout_notebook_bot.utils.Constants;

import java.util.Arrays;
import java.util.List;

import static ru.elessarov.workout_notebook_bot.utils.BotUtils.*;

@Service
@AllArgsConstructor
@Slf4j
public class CommandHandler {
    private final SubscribeService subscribeService;

    public SendMessage handleCommands(Update update) {
        Command command = Command.of(update.getMessage().getText());
        SendMessage message = null;
        switch (command) {
            case START -> message = sendGreetingMessage(update);
            case HELP -> message = sendHelpMessage(update);
            case TRAININGS -> message = sendTrainingMessage(update);
            case MY_TRAININGS -> message = sendMyTrainingMessage(update);
            case SUBSCRIPTION -> message = sendSubscriptionMessage(update);
            case UNKNOWN -> message = sendUnknownMessage(update);
        }
        return message;
    }

    private SendMessage sendHelpMessage(Update update) {
        String id = getChatId(update);
        return new SendMessage(id, getHelpText());
    }

    private SendMessage sendGreetingMessage(Update update) {
        String id = getChatId(update);
        String username = update.getMessage().getFrom().getUserName();
        return new SendMessage(id, getGreetingText(username));
    }

    private String getHelpText() {
        return String.format(
                Constants.HELP_BLOCK,
                Command.TRAININGS.getName(),
                Command.TRAININGS.getDescription(),
                Command.MY_TRAININGS.getName(),
                Command.MY_TRAININGS.getDescription(),
                Command.SUBSCRIPTION.getName(),
                Command.SUBSCRIPTION.getDescription(),
                Command.HELP.getName(),
                Command.HELP.getDescription());
    }

    private String getGreetingText(String username) {
        return (Constants.GREETING_TEXT).formatted(username, Command.HELP.getName());
    }

    public SendMessage sendUnknownMessage(Update update) {
        return new SendMessage(getChatId(update), Constants.CANT_UNDERSTAND);
    }

    private SendMessage sendTrainingMessage(Update update) {
        String chatId = getChatId(update);
        SendMessage sendMessage = new SendMessage(chatId, Constants.CHOOSE_TRAINING_TYPE);
        List<String> keyboardList = Arrays.stream(TrainingType.values())
                .map(TrainingType::getDescription)
                .toList();
        BotUtils.addKeyboard(sendMessage, keyboardList);
        return sendMessage;
    }

    private SendMessage sendMyTrainingMessage(Update update) {
        return new SendMessage(getChatId(update), "Здесь будет информация о твоих сохраненных тренировках");
    }

    private SendMessage sendSubscriptionMessage(Update update) {
        return subscribeService.checkUserSubscribe(update);
    }
}
