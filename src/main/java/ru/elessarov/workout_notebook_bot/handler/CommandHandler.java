package ru.elessarov.workout_notebook_bot.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.elessarov.workout_notebook_bot.api.model.TrainingType;
import ru.elessarov.workout_notebook_bot.utils.Command;
import ru.elessarov.workout_notebook_bot.utils.Constants;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class CommandHandler implements BasicActions {

    public SendMessage handleCommands(Update update) {
        Command command = Command.of(update.getMessage().getText());
       switch (command) {
           case START -> {
               return sendGreetingMessage(update);
           }
           case HELP -> {
               return  sendHelpMessage(update);
           }
           case TRAININGS ->  {
               return sendTrainingMessage(update);
           }
       }
       return sendUnknownMessage(update);
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
        return String.format("""
                        %s - %s\s
                        %s - %s\s
                        %s - %s\s
                         %s - %s""",
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
        return ("Привет %s,\n" +
                "если нужна помощь с командами воспользуйся командой - %s").formatted(username, Command.HELP.getName());
    }

    public SendMessage sendUnknownMessage(Update update) {
        return new SendMessage(getChatId(update), Constants.CANT_UNDERSTAND);
    }

    private SendMessage sendTrainingMessage(Update update) {
        String chatId = getChatId(update);
        SendMessage sendMessage = new SendMessage(chatId, "Выбери вид тренировки");
        List<String> keyboardList = Arrays.stream(TrainingType.values())
                .map(TrainingType::getDescription)
                .toList();
        addKeyboard(sendMessage, keyboardList);
        return sendMessage;
    }
}
