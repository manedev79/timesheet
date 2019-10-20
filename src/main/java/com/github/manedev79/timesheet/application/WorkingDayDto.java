package com.github.manedev79.timesheet.application;

import com.github.manedev79.timesheet.domain.WorkingDay;
import lombok.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WorkingDayDto {

    private LocalDate day;

    private Instant start;

    private Instant end;

    @Builder.Default
    private Duration flextime = Duration.ZERO;

    private String description;

    @Builder.Default
    private List<BreakDto> breaks = new ArrayList<>();

    @Builder.Default
    private Duration totalBreak = Duration.ZERO;

    @Builder.Default
    private Duration totalWork = Duration.ZERO;

    WorkingDay toEntity() {
        return new WorkingDay(day, start, end, Duration.ZERO, description,
                breaks.stream().map(BreakDto::fromDto).collect(toList()));
    }

    static WorkingDayDto fromEntity(final WorkingDay workingDay) {

        final List<BreakDto> breakDtos = workingDay.getBreaks().stream()
                .map(BreakDto::toDto)
                .collect(toList());

        return new WorkingDayDto(workingDay.getDay(),
                workingDay.getStart(),
                workingDay.getEnd(),
                workingDay.getFlextime(),
                workingDay.getDescription(),
                breakDtos,
                workingDay.getTotalBreaksDuration(),
                workingDay.getTotalWorkDuration());
    }
}
