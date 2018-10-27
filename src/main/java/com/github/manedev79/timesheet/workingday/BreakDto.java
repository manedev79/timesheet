package com.github.manedev79.timesheet.workingday;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;

@Data
@AllArgsConstructor
public class BreakDto {

    private Long id;
    private Instant start;
    private Instant end;
    private Duration duration;

    public static BreakDto toDto(final Break aBreak) {
        return new BreakDto(aBreak.getId(), aBreak.getStart(), aBreak.getEnd(), aBreak.getDuration());
    }
}
