package com.github.manedev79.timesheet.workingday;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("monthlytimesheets")
public class TimesheetController {

    private WorkingDayService workingDayService;

    public TimesheetController(WorkingDayService workingDayService) {
        this.workingDayService = workingDayService;
    }

    @GetMapping(params = "yearMonth")
    public List<WorkingDayDto> getTimesheetForMonth(@RequestParam("yearMonth") final String yearMonth) {
        return workingDayService.getWorkingDaysForMonth(yearMonth).stream()
                .map(WorkingDayDto::toDto)
                .collect(toList());
    }
}
