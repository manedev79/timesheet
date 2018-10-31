package com.github.manedev79.timesheet.workingday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
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
    private List<Break> breaks;

    List<Break> getBreaks() {
        return unmodifiableList(breaks);
    }
}
