package com.github.manedev79.timesheet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkingDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate day;

    private Instant start;

    private Instant end;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "workingDayId")
    private List<Break> breaks = new ArrayList<>();

    public List<Break> getBreaks() {
        return unmodifiableList(breaks);
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
