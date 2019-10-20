package com.github.manedev79.timesheet.fixtures;

import com.github.manedev79.timesheet.application.BreakDto;
import com.github.manedev79.timesheet.application.WorkingDayDto;

import java.time.*;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class TestFixtures {
    public static final String HACKTOBER_STRING = "2018-10";
    public static final YearMonth HACKTOBER = YearMonth.parse(HACKTOBER_STRING);
    public static final LocalDate HACKTOBER_LAST_DAY_DATE = LocalDate.of(HACKTOBER.getYear(), HACKTOBER.getMonth(), 31);
    public static final MonthDay HACKTOBER_LAST_DAY = MonthDay.from(HACKTOBER_LAST_DAY_DATE);
    public static final Instant HACKTOBER_LAST_DAY_MIDNIGHT = Instant.ofEpochSecond(HACKTOBER_LAST_DAY_DATE.toEpochDay() * 24 * 60 * 60);
    private static final Duration FLEX_TIME = Duration.ZERO;

    public static WorkingDayDto createWorkingDayWithBreak() {
        BreakDto newBreak = createBreak();
        WorkingDayDto workingDay = createWorkingDay();
        workingDay.setBreaks(singletonList(newBreak));
        return workingDay;
    }

    public static BreakDto createBreak() {
        Instant breakStart = HACKTOBER_LAST_DAY_MIDNIGHT.plus(11 * 60 + 30, MINUTES);
        Instant breakEnd = HACKTOBER_LAST_DAY_MIDNIGHT.plus(12 * 60 + 15, MINUTES);
        return new BreakDto(breakStart, breakEnd);
    }

    public static WorkingDayDto createWorkingDay() {
        Duration totalWorkDuration = Duration.ofHours(8);
        Instant end = HACKTOBER_LAST_DAY_MIDNIGHT.plus(totalWorkDuration);
        return WorkingDayDto.builder()
                            .day(HACKTOBER_LAST_DAY_DATE)
                            .start(HACKTOBER_LAST_DAY_MIDNIGHT)
                            .end(end)
                            .flextime(FLEX_TIME)
                            .description("Test day")
                            .breaks(emptyList())
                            .totalWork(totalWorkDuration)
                            .build();
    }

    public static WorkingDayDto createLongWorkingDay() {
        Duration totalWorkDuration = Duration.ofHours(9);
        Instant end = HACKTOBER_LAST_DAY_MIDNIGHT.plus(totalWorkDuration);
        return createWorkingDay().toBuilder()
                                 .end(end)
                                 .totalWork(totalWorkDuration)
                                 .build();
    }
}
