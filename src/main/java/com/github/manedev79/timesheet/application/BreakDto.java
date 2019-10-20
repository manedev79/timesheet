package com.github.manedev79.timesheet.application;

import com.github.manedev79.timesheet.domain.Break;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;

// TOOD: Refactor into two classes:
// 1. Break with start / end
// 2. Break with duration
@Data
@NoArgsConstructor
public class BreakDto {
    private Instant start;
    private Instant end;
    private Duration duration;

    public BreakDto(Instant start, Instant end) {
        this.start = start;
        this.end = end;
        this.duration = Duration.between(start, end);
    }

    private BreakDto(Duration duration) {
        this.duration = duration;
    }

    static BreakDto toDto(final Break aBreak) {
        if (aBreak.getStart() != null || aBreak.getEnd() != null) {
            return new BreakDto(aBreak.getStart(), aBreak.getEnd());
        }
        return new BreakDto(aBreak.getDuration());
    }

    Break fromDto() {
        if (start != null && end != null) {
            return new Break(start, end, Duration.between(start, end));
        }
        return new Break(start, end, duration);
    }
}
