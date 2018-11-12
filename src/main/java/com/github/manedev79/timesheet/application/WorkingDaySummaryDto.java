package com.github.manedev79.timesheet.application;

import com.github.manedev79.timesheet.domain.WorkingDay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkingDaySummaryDto {

    private Long id;

    private LocalDate day;

    private Instant start;

    private Instant end;

    private Duration totalWork;

    private Duration totalBreak;

    private String description;

    public static WorkingDaySummaryDto toDto(final WorkingDay workingDay) {
        return new WorkingDaySummaryDto(workingDay.getId(), workingDay.getDay(),
                workingDay.getStart(), workingDay.getEnd(),
                workingDay.getTotalWorkDuration(),
                workingDay.getTotalBreaksDuration(),
                workingDay.getDescription());
    }

    static WorkingDaySummaryDto emptyWorkingDay(LocalDate day) {
        return new WorkingDaySummaryDto(null, day, null, null, null, null, null);
    }
}
