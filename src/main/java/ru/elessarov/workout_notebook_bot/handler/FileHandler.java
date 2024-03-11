package ru.elessarov.workout_notebook_bot.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.elessarov.workout_notebook_bot.api.model.CustomMessage;
import ru.elessarov.workout_notebook_bot.api.properties.AdminProperties;
import ru.elessarov.workout_notebook_bot.converter.ExerciseConverter;
import ru.elessarov.workout_notebook_bot.service.ExerciseService;
import ru.elessarov.workout_notebook_bot.service.FileParser;

import java.io.File;

import static ru.elessarov.workout_notebook_bot.utils.BotUtils.getChatId;
import static ru.elessarov.workout_notebook_bot.utils.BotUtils.isUserAdmin;

@Service
@AllArgsConstructor
public class FileHandler {
    private final FileParser fileParser;
    private final ExerciseConverter exerciseConverter;
    private final ExerciseService exerciseService;
    private final AdminProperties adminProperties;

    public CustomMessage updateData(Update update, File file) {
        var tgUser = update.getMessage().getFrom();
        isUserAdmin(tgUser, adminProperties.getStringAdminId());
        var data = fileParser.extractExercisesFromFile(file);
        data.stream()
                .map(exerciseConverter::toEntity)
                .map(exerciseService::saveExercise)
                .toList();

        var message = new SendMessage(getChatId(update), "Данные успешно сохранены");
        var adminMessage = new SendMessage(String.valueOf(adminProperties.getAdminId()), "Пользователь @%s обновил данные бд".formatted(tgUser.getUserName()));

        return new CustomMessage(message, adminMessage);
    }
}
