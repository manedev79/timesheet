package com.github.manedev79.timesheet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Break {

    private Instant start;

    private Instant end;

    private Duration duration;
}
