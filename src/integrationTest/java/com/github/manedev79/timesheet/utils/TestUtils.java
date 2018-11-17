package com.github.manedev79.timesheet.utils;

import com.github.manedev79.timesheet.application.BreakDto;
import com.github.manedev79.timesheet.application.WorkingDayDto;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class TestUtils {
    private static final YearMonth HACKTOBER = YearMonth.of(2018, 10);
    public static final LocalDate HACKTOBER_LAST_DAY = LocalDate.of(HACKTOBER.getYear(), HACKTOBER.getMonth(), 31);
    public static final Instant HACKTOBER_LAST_DAY_MIDNIGHT = Instant.ofEpochSecond(HACKTOBER_LAST_DAY.toEpochDay() * 24 * 60 * 60);

    public static WorkingDayDto createWorkingDayWithBreak() {
        BreakDto newBreak = createBreak();
        WorkingDayDto workingDay = createWorkingDay();
        workingDay.setBreaks(singletonList(newBreak));
        return workingDay;
    }

    public static BreakDto createBreak() {
        Instant breakStart = HACKTOBER_LAST_DAY_MIDNIGHT.plus(11 * 60 + 30, MINUTES);
        Instant breakEnd = HACKTOBER_LAST_DAY_MIDNIGHT.plus(12 * 60 + 15, MINUTES);
        return new BreakDto(null, breakStart, breakEnd, null);
    }

    public static WorkingDayDto createWorkingDay() {
        Instant end = HACKTOBER_LAST_DAY_MIDNIGHT.plus(Duration.ofHours(8));
        return new WorkingDayDto(null, HACKTOBER_LAST_DAY, HACKTOBER_LAST_DAY_MIDNIGHT, end, "Test day", emptyList());
    }
}
