package com.github.manedev79.timesheet.adapters.secondary.persistence;

import com.github.manedev79.timesheet.domain.WorkingHoursConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties("timesheet")
@Setter
@Getter
public class TimesheetConfiguration implements WorkingHoursConfiguration {
    private Duration dailyWorkingHours;
}


