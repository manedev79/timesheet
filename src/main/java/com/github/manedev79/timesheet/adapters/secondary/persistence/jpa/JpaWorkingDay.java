package com.github.manedev79.timesheet.adapters.secondary.persistence.jpa;

import com.github.manedev79.timesheet.domain.Break;
import com.github.manedev79.timesheet.domain.WorkingDay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
class JpaWorkingDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate day;

    private Instant start;

    private Instant end;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "workingDayId")
    private List<JpaBreak> breaks = new ArrayList<>();

    static JpaWorkingDay fromDomainObject(WorkingDay workingDay) {
        return new JpaWorkingDay(workingDay.getId(),
                workingDay.getDay(),
                workingDay.getStart(),
                workingDay.getEnd(),
                workingDay.getDescription(),
                fromDomainObjects(workingDay.getBreaks()));
    }

    private static List<JpaBreak> fromDomainObjects(List<Break> breaks) {
        return breaks.stream().map(JpaBreak::fromDomainObject).collect(toList());
    }

    WorkingDay toDomainObject() {
        return new WorkingDay(id, day, start, end, description, toDomainEntities(breaks));
    }

    private static List<Break> toDomainEntities(List<JpaBreak> breaks) {
        return breaks.stream().map(JpaBreak::toDomainObject).collect(toList());
    }
}
