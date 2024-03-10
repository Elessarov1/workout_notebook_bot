package ru.elessarov.workout_notebook_bot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.elessarov.workout_notebook_bot.api.model.ExerciseModel;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

@Service
@AllArgsConstructor
public class FileParser {
    private final ObjectMapper objectMapper;


    public List<ExerciseModel> extractExercisesFromFile(File file) {
        try {
            String jsonString = Files.readString(file.toPath(), StandardCharsets.UTF_8);
            return objectMapper.readValue(jsonString, new TypeReference<>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
