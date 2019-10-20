package com.github.manedev79.timesheet.adapters.primary.rest;

import com.github.manedev79.timesheet.application.TimesheetDto;
import com.github.manedev79.timesheet.application.TimesheetService;
import com.github.manedev79.timesheet.application.WorkingDayDto;
import com.github.manedev79.timesheet.domain.Timesheet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.MonthDay;
import java.time.YearMonth;

@RequiredArgsConstructor
@RestController
@RequestMapping("timesheet")
public class TimesheetController {

    private final TimesheetService timesheetService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Timesheet createTimesheet(YearMonth yearMonth) {
        return timesheetService.createTimesheetForMonth(yearMonth);
    }

    @PostMapping(path = "/{month}/workingday")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateWorkingDay(@PathVariable("month") String monthString, @RequestBody WorkingDayDto workingDay) {
        YearMonth month = YearMonth.parse(monthString);
        timesheetService.updateWorkingDay(workingDay, month);
    }

    @GetMapping(path = "/{month}")
    public TimesheetDto getTimesheet(@PathVariable("month") String monthString) {
        YearMonth month = YearMonth.parse(monthString);
        return timesheetService.getTimesheetForMonth(month);
    }

    @GetMapping(path = "/{month}/workingDay/{day}")
    public WorkingDayDto getWorkingDay(@PathVariable("month") String monthString, @PathVariable("day") String dayString) {
        YearMonth month = YearMonth.parse(monthString);
        MonthDay day = MonthDay.parse(String.format("--%s", dayString));
        return timesheetService.getWorkingDay(month, day);
    }
}
