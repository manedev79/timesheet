package com.github.manedev79.timesheet.domain;

import com.github.manedev79.timesheet.application.WorkingDaySummaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class FlexTimeDomainService {
    @Autowired
    private WorkingHoursConfiguration configuration;

    public void flexTimeForeDay(WorkingDaySummaryDto workingDay) {
        Duration totalWork = workingDay.getTotalWork();
        if (totalWork != null) {
            workingDay.setFlexTime(totalWork.minus(configuration.getDailyHours()));
        }
    }
}
