package ru.elessarov.workout_notebook_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import ru.elessarov.workout_notebook_bot.config.BotProperties;

@SpringBootApplication
@EnableConfigurationProperties(BotProperties.class)
public class WorkoutNotebookBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkoutNotebookBotApplication.class, args);
    }
}
