package ru.elessarov.workout_notebook_bot.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;
import ru.elessarov.workout_notebook_bot.api.entity.MuscleGroup;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Converter
public class MuscleGroupConverter implements AttributeConverter<List<MuscleGroup>, String> {

    @Override
    public String convertToDatabaseColumn(final List<MuscleGroup> attribute) {
        List<String> groupValues = attribute
                .stream()
                .map(MuscleGroup::getName)
                .toList();

        return String.join(",", groupValues);
    }

    @Override
    public List<MuscleGroup> convertToEntityAttribute(final String dbData) {
        if (dbData.isBlank()) {
            return Collections.emptyList();
        }
        List<String> groupValues = Arrays.asList(dbData.split(","));

        return groupValues.stream()
                .map(MuscleGroup::of)
                .collect(Collectors.toList());
    }
}
