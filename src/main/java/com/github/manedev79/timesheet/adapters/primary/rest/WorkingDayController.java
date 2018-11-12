package com.github.manedev79.timesheet.adapters.primary.rest;

import com.github.manedev79.timesheet.application.BreakDto;
import com.github.manedev79.timesheet.application.WorkingDayDto;
import com.github.manedev79.timesheet.application.WorkingDayService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("workingdays")
public class WorkingDayController {

    private WorkingDayService workingDayService;

    public WorkingDayController(final WorkingDayService workingDayService) {
        this.workingDayService = workingDayService;
    }

    @GetMapping
    public List<WorkingDayDto> getAllWorkingDays() {
        return workingDayService.getAllWorkingDays();
    }

    @GetMapping(path = "/{id}")
    public WorkingDayDto getWorkingDay(@PathVariable("id") final Long id) {
        return getOneWorkingDay(id);
    }

    @GetMapping(path = "/{id}/breaks")
    public List<BreakDto> getAllBreaksForWorkingDay(@PathVariable("id") final Long id) {
        return getOneWorkingDay(id).getBreaks();
    }

    @GetMapping(path = "/{workingDayId}/breaks/{breakId}")
    public BreakDto getBreakForWorkingDay(@PathVariable("workingDayId") final Long workingDayId,
                                          @PathVariable("breakId") final Long breakId) {
        return getOneWorkingDay(workingDayId)
                .getBreaks().stream()
                .filter(aBreak -> breakId.equals(aBreak.getId()))
                .findFirst().orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(params = "day")
    public WorkingDayDto getWorkingDay(@RequestParam("day") final LocalDate day) {
        return workingDayService.getWorkingDay(day).orElseThrow(ResourceNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public WorkingDayDto addWorkingDay(@RequestBody final WorkingDayDto workingDayDto) {
        return workingDayService.addWorkingDay(workingDayDto);
    }

    @PutMapping
    @ResponseStatus(NO_CONTENT)
    public void updateWorkingDay(@RequestBody final WorkingDayDto workingDayDto) {
        workingDayService.updateWorkingDay(workingDayDto);
    }

    private WorkingDayDto getOneWorkingDay(final Long id) {
        return workingDayService.getWorkingDay(id).orElseThrow(ResourceNotFoundException::new);
    }
}
