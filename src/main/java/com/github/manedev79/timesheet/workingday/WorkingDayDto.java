package com.github.manedev79.timesheet.workingday;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@AllArgsConstructor
public class WorkingDayDto {

    private Long id;

    private LocalDate day;

    private Instant start;

    private Instant end;

    private String description;

    private List<BreakDto> breaks;

    static WorkingDayDto toDto(final WorkingDay workingDay) {

        final List<BreakDto> breakDtos = workingDay.getBreaks().stream()
                .map(BreakDto::toDto)
                .collect(toList());

        return new WorkingDayDto(workingDay.getId(), workingDay.getDay(), workingDay.getStart(),
                workingDay.getEnd(), workingDay.getDescription(), breakDtos);
    }
}
