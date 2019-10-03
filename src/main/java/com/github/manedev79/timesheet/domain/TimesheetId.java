package com.github.manedev79.timesheet.domain;

import lombok.*;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class TimesheetId {
    private final String id = UUID.randomUUID().toString();
}