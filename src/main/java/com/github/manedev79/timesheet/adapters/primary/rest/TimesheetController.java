package com.github.manedev79.timesheet.adapters.primary.rest;

import com.github.manedev79.timesheet.application.TimesheetDto;
import com.github.manedev79.timesheet.application.TimesheetService;
import com.github.manedev79.timesheet.application.WorkingDayDto;
import com.github.manedev79.timesheet.domain.Timesheet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "{month}/workingDay/{monthDay}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateWorkingDay(WorkingDayDto workingDay) {
        timesheetService.updateWorkingDay(workingDay);
    }

    @GetMapping(path = "{month}")
    public TimesheetDto getTimesheet(@RequestParam YearMonth month) {
        return timesheetService.getTimesheetForMonth(month);
    }
}
