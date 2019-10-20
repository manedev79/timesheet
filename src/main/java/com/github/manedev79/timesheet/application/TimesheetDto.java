package com.github.manedev79.timesheet.application;

import com.github.manedev79.timesheet.domain.Timesheet;
import com.github.manedev79.timesheet.domain.WorkingDay;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.MonthDay;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Data
public class TimesheetDto {
    private final YearMonth yearMonth;
    private final List<WorkingDayDto> workingDays;

    static TimesheetDto fromEntity(final Timesheet timesheet) {
        List<WorkingDayDto> days = timesheet.getWorkingDays().values()
                                            .stream()
                                            .map(WorkingDayDto::fromEntity)
                                            .collect(Collectors.toList());
        return new TimesheetDto(timesheet.getYearMonth(), days);
    }

    public Timesheet toEntity() {
        Timesheet timesheet = new Timesheet(yearMonth);
        Map<MonthDay, WorkingDay> dayEntities = timesheet.getWorkingDays();

        workingDays.forEach(workingDayDto -> dayEntities.put(MonthDay.from(workingDayDto.getDay()), workingDayDto.toEntity()));

        return timesheet;
    }

    public WorkingDayDto getWorkingDay(MonthDay day) {
        return workingDays.get(day.getDayOfMonth() - 1);
    }
}
