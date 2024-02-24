package ru.elessarov.workout_notebook_bot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("bot")
public class BotProperties {
    private String name;
    private String token;
}
