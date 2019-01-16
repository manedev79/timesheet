package com.github.manedev79.timesheet.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component
@ConfigurationProperties
public class WorkingHoursConfiguration {

    @DurationUnit(ChronoUnit.HOURS)
    private Duration dailyHours = Duration.ofHours(8);

    public void setDailyHours(Duration dailyHours) {
        this.dailyHours = dailyHours;
    }

    public Duration getDailyHours() {
        return dailyHours;
    }
}
