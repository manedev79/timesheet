package com.github.manedev79.timesheet.adapters.secondary.persistence.jpa;

import com.github.manedev79.timesheet.domain.Break;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Duration;
import java.time.Instant;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
class JpaBreak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant start;

    private Instant end;

    private Duration duration;

    private Long workingDayId;

    static JpaBreak fromDomainObject(Break aBreak) {
        return new JpaBreak(
                aBreak.getId(),
                aBreak.getStart(),
                aBreak.getEnd(),
                aBreak.getDuration(),
                aBreak.getWorkingDayId());
    }

    Break toDomainObject() {
        return new Break(id, start, end, duration, workingDayId);
    }
}
