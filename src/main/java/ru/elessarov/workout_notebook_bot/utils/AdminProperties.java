package ru.elessarov.workout_notebook_bot.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AdminProperties {

    public static String ADMIN_ID;

    @Value("${constants.admin_id}")
    public void setAdminStatic(String adminId) {
        AdminProperties.ADMIN_ID = adminId;
    }
}
