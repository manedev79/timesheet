package com.github.manedev79.timesheet.workingday;

import com.github.manedev79.timesheet.restutil.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static com.github.manedev79.timesheet.workingday.WorkingDayDto.toDto;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("workingdays")
public class WorkingDayController {

    private WorkingDayService workingDayService;

    public WorkingDayController(final WorkingDayService workingDayService) {
        this.workingDayService = workingDayService;
    }

    @GetMapping
    public List<WorkingDayDto> getAllWorkingDays() {
        return workingDayService.getAllWorkingDays().stream()
                .map(WorkingDayDto::toDto)
                .collect(toList());
    }

    @GetMapping(path = "/{id}")
    public WorkingDayDto getWorkingDay(@PathVariable("id") final Long id) {
        return toDto(getOneWorkingDay(id));
    }

    @GetMapping(path = "/{id}/breaks")
    public List<BreakDto> getAllBreaksForWorkingDay(@PathVariable("id") final Long id) {
        return getOneWorkingDay(id)
                .getBreaks().stream()
                .map(BreakDto::toDto)
                .collect(toList());
    }

    @GetMapping(path = "/{workingDayId}/breaks/{breakId}")
    public BreakDto getBreakForWorkingDay(@PathVariable("workingDayId") final Long workingDayId,
                                          @PathVariable("breakId") final Long breakId) {
        final Break foundBreak = getOneWorkingDay(workingDayId)
                .getBreaks().stream()
                .filter(aBreak -> breakId.equals(aBreak.getId()))
                .findFirst().orElseThrow(ResourceNotFoundException::new);
        return BreakDto.toDto(foundBreak);
    }

    @GetMapping(params = "day")
    public WorkingDayDto getWorkingDay(@RequestParam("day") final LocalDate day) {
        final WorkingDay workingDay = workingDayService.getWorkingDay(day).orElseThrow(ResourceNotFoundException::new);
        return toDto(workingDay);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public WorkingDayDto addWorkingDay(@RequestBody final WorkingDayDto workingDayDto) {
        return WorkingDayDto.toDto(workingDayService.addWorkingDay(workingDayDto.fromDto()));
    }

    private WorkingDay getOneWorkingDay(final Long id) {
        return workingDayService.getWorkingDay(id).orElseThrow(ResourceNotFoundException::new);
    }
}
