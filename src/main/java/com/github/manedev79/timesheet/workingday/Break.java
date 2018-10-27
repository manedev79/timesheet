package com.github.manedev79.timesheet.workingday;

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
public class Break {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant start;

    private Instant end;

    private Duration duration;

    private Long workingDayId;
}
