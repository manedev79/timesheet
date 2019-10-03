package com.github.manedev79.timesheet.application;

import com.github.manedev79.timesheet.domain.WorkingDay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkingDayDto {

    private LocalDate day;

    private Instant start;

    private Instant end;

    private Duration flextime;

    private String description;

    private List<BreakDto> breaks;

    WorkingDay toEntity() {
        return new WorkingDay(day, start, end, Duration.ZERO, description,
                breaks.stream().map(BreakDto::fromDto).collect(toList()));
    }

    static WorkingDayDto fromEntity(final WorkingDay workingDay) {

        final List<BreakDto> breakDtos = workingDay.getBreaks().stream()
                .map(BreakDto::toDto)
                .collect(toList());

        return new WorkingDayDto(workingDay.getDay(), workingDay.getStart(),
                workingDay.getEnd(), workingDay.getFlextime(), workingDay.getDescription(), breakDtos);
    }
}
