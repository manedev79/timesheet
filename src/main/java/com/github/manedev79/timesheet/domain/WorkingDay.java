package com.github.manedev79.timesheet.domain;

import lombok.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkingDay {

    private LocalDate day;

    private Instant start;

    private Instant end;

    private Duration flextime = Duration.ZERO;

    private String description;

    private List<Break> breaks = new ArrayList<>();

    public List<Break> getBreaks() {
        return unmodifiableList(breaks);
    }

    public WorkingDay(LocalDate day) {
        this.day = day;
    }

    public Duration getTotalBreaksDuration() {
        return breaks.stream()
                .map(Break::getDuration)
                .reduce(Duration::plus)
                .orElse(Duration.ZERO);
    }

    public Duration getTotalWorkDuration() {
        if (isWorkDone()) {
            return Duration.between(start, end).minus(getTotalBreaksDuration());
        }
        else {
            return Duration.ZERO;
        }
    }

    private boolean isWorkDone() {
        return start != null && end != null;
    }

}
