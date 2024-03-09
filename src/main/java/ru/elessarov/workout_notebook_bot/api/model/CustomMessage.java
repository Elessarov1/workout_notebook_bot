package ru.elessarov.workout_notebook_bot.api.model;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public record CustomMessage(SendMessage message, SendMessage adminMessage) {

    public boolean hasAdminMessage() {
        return adminMessage != null;
    }
}
