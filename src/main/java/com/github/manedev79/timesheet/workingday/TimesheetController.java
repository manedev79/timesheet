package com.github.manedev79.timesheet.workingday;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("monthlytimesheets")
public class TimesheetController {

    private WorkingDayService workingDayService;

    public TimesheetController(WorkingDayService workingDayService) {
        this.workingDayService = workingDayService;
    }

    @Transactional
    @GetMapping(params = "date")
    public List<WorkingDaySummaryDto> getTimesheetForMonthByDate(@RequestParam("date") final LocalDate date) {
        LocalDate start = date.withDayOfMonth(1);
        LocalDate end = date.withDayOfMonth(date.lengthOfMonth());

        return workingDayService.getWorkingDaysBetween(start, end).stream()
                .map(WorkingDaySummaryDto::toDto)
                .collect(toList());
    }

    @Transactional
    @GetMapping(params = "yearMonth")
    public List<WorkingDaySummaryDto> getTimesheetForYearMonth(@RequestParam("yearMonth") final YearMonth yearMonth) {
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        return workingDayService.getWorkingDaysBetween(start, end).stream()
                .map(WorkingDaySummaryDto::toDto)
                .collect(toList());
    }


}
