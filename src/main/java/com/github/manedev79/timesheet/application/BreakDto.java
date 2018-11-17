package com.github.manedev79.timesheet.application;

import com.github.manedev79.timesheet.domain.Break;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreakDto {

    private Long id;
    private Instant start;
    private Instant end;
    private Duration duration;

    static BreakDto toDto(final Break aBreak) {
        return new BreakDto(aBreak.getId(), aBreak.getStart(), aBreak.getEnd(), aBreak.getDuration());
    }

    Break fromDto(final Long workingDayId) {
        if (start != null && end != null) {
            return new Break(id, start, end, Duration.between(start, end), workingDayId);
        }
        return new Break(id, start, end, duration, workingDayId);
    }
}