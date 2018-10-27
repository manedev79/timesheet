package com.github.manedev79.timesheet.workingday;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Duration;
import java.time.Instant;

@Entity
@Getter
public class Break {

    @Id
    private Long id;

    private Instant start;

    private Instant end;

    private Duration duration;

    private Long workingDayId;
}
