package ru.elessarov.workout_notebook_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.elessarov.workout_notebook_bot.api.config.BotProperties;

@SpringBootApplication
@EnableConfigurationProperties(BotProperties.class)
public class WorkoutNotebookBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkoutNotebookBotApplication.class, args);
    }
}
