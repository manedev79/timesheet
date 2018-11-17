package com.github.manedev79.timesheet.adapters.primary.rest;

import com.github.manedev79.timesheet.application.TimesheetService;
import com.github.manedev79.timesheet.application.WorkingDaySummaryDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("monthlytimesheets")
public class TimesheetController {

    private TimesheetService timesheetService;

    public TimesheetController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    @GetMapping(params = "date")
    public List<WorkingDaySummaryDto> getTimesheetForMonthByDate(@RequestParam("date") final LocalDate date) {
        LocalDate start = date.withDayOfMonth(1);
        LocalDate end = date.withDayOfMonth(date.lengthOfMonth());

        return timesheetService.getWorkingDaysBetween(start, end);
    }

    @GetMapping(params = "yearMonth")
    public List<WorkingDaySummaryDto> getTimesheetForYearMonth(@RequestParam("yearMonth") final YearMonth yearMonth) {
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        return timesheetService.getWorkingDaysBetween(start, end);
    }


}
