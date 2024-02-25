package ru.elessarov.workout_notebook_bot.api.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotConfig {
    private static final String BOT_NAME = "workoutBot";
    private final ApplicationContext context;

    public BotConfig(ApplicationContext context) {
        this.context = context;
    }

    @PostConstruct
    void init() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(context.getBean(BOT_NAME, TelegramLongPollingBot.class));
    }
}
