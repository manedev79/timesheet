package com.github.manedev79.timesheet.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This is an aggregate root.
 * All operations on timesheet go here.
 * Only immutable value objects may be returned.
 */
@Document
@EqualsAndHashCode
@ToString
@Getter
public class Timesheet {
    @Id
    @Indexed(unique = true)
    private final YearMonth yearMonth;
    private Map<MonthDay, WorkingDay> workingDays = new HashMap<>();

    public Timesheet(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
        initializeWorkingDays();
    }

    private void initializeWorkingDays() {
        workingDays.putAll(IntStream.rangeClosed(1, yearMonth.lengthOfMonth())
                                    .mapToObj(day -> MonthDay.of(yearMonth.getMonth(), day))
                                    .collect(Collectors.toMap(
                                            monthDay -> monthDay,
                                            monthDay -> new WorkingDay(of(yearMonth, monthDay)))));
    }

    private LocalDate of(YearMonth year, MonthDay day) {
        return LocalDate.of(year.getYear(), year.getMonth(), day.getDayOfMonth());
    }
}