package ru.elessarov.workout_notebook_bot.api.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("admin")
public class AdminProperties {
    private long adminId;

    public String getStringAdminId() {
        return String.valueOf(adminId);
    }
}
