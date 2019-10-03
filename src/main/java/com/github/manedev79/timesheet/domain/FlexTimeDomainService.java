package com.github.manedev79.timesheet.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class FlexTimeDomainService {
    @Autowired
    private WorkingHoursConfiguration configuration;

    public void flexTimeForDay(WorkingDay workingDay) {
        Duration totalWork = workingDay.getTotalWorkDuration();
        if (totalWork != null) {
            workingDay.setFlextime(totalWork.minus(configuration.getDailyWorkingHours()));
        }
    }
}
