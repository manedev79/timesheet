package com.github.manedev79.timesheet.application;

import com.github.manedev79.timesheet.adapters.secondary.persistence.TimesheetRepository;
import com.github.manedev79.timesheet.domain.FlexTimeDomainService;
import com.github.manedev79.timesheet.domain.Timesheet;
import com.github.manedev79.timesheet.domain.WorkingDay;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.MonthDay;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class TimesheetService {
    private final TimesheetRepository timesheetRepository;
    private final FlexTimeDomainService flexTimeDomainService;

    public TimesheetDto getTimesheetForMonth(YearMonth month) {
        return TimesheetDto.fromEntity(getTimesheetEntity(month));
    }

    private Timesheet getTimesheetEntity(YearMonth month) {
        return timesheetRepository.findByYearMonth(month).orElse(new Timesheet(month));
    }

    public Timesheet createTimesheetForMonth(YearMonth yearMonth) {
        return timesheetRepository.save(new Timesheet(yearMonth));
    }

    public void updateWorkingDay(WorkingDayDto workingDay, YearMonth month) {
        Timesheet timesheet = getTimesheetEntity(month);
        WorkingDay workingDayEntity = workingDay.toEntity();
        flexTimeDomainService.flexTimeForDay(workingDayEntity);

        MonthDay day = MonthDay.from(workingDay.getDay());
        timesheet.getWorkingDays().put(day, workingDayEntity);
        timesheetRepository.save(timesheet);
    }

    public WorkingDayDto getWorkingDay(YearMonth month, MonthDay day) {
        TimesheetDto timesheet = getTimesheetForMonth(month);
        return timesheet.getWorkingDay(day);
    }
}
