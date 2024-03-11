package ru.elessarov.workout_notebook_bot.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ExternalKey {

    public static String ADMIN_ID;

    @Value("${constants.admin}")
    public void setAdminStatic(String admin_id) {
        ExternalKey.ADMIN_ID = admin_id;
    }
}
