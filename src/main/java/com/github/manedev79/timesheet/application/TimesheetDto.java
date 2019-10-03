package com.github.manedev79.timesheet.application;

import com.github.manedev79.timesheet.domain.Timesheet;
import com.github.manedev79.timesheet.domain.WorkingDay;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.MonthDay;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Data
public class TimesheetDto {
    private final YearMonth yearMonth;
    private final Map<MonthDay, WorkingDayDto> workingDays;

    static TimesheetDto fromEntity(final Timesheet timesheet) {
        Map<MonthDay, WorkingDayDto> days = new HashMap<>();

        timesheet.getWorkingDays().forEach(
                (monthDay, workingDay) -> days.put(monthDay, WorkingDayDto.fromEntity(workingDay))
        );

        return new TimesheetDto(timesheet.getYearMonth(), days);
    }

    public Timesheet toEntity() {
        Timesheet timesheet = new Timesheet(yearMonth);
        Map<MonthDay, WorkingDay> dayEntities = timesheet.getWorkingDays();

        workingDays.forEach(
                (monthDay, workingDayDto) -> dayEntities.put(monthDay, workingDayDto.toEntity())
        );

        return timesheet;
    }

    public WorkingDayDto getWorkingDay(MonthDay day) {
        return workingDays.get(day);
    }
}
