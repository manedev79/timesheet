package com.github.manedev79.timesheet.jpautil;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, Long> {

    @Override
    public Long convertToDatabaseColumn(final Duration duration) {
        return duration != null ? duration.toMillis() : null;
    }

    @Override
    public Duration convertToEntityAttribute(final Long duration) {
        return duration != null ? Duration.of(duration, ChronoUnit.MILLIS) : null;
    }
}