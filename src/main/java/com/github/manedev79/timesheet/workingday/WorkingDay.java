package com.github.manedev79.timesheet.workingday;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@Entity
@Getter
public class WorkingDay {

    @Id
    private Long id;

    private LocalDate day;

    private Instant start;

    private Instant end;

    private String description;

    @OneToMany(mappedBy="workingDayId", targetEntity=Break.class)
    private List<Break> breaks;

    List<Break> getBreaks() {
        return unmodifiableList(breaks);
    }

}
