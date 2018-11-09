package com.github.manedev79.timesheet.workingday;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimesheetApplicationTests {

    private static final YearMonth HACKTOBER = YearMonth.of(2018, 10);
    private static final LocalDate HACKTOBER_LAST_DAY = LocalDate.of(HACKTOBER.getYear(), HACKTOBER.getMonth(), 31);
    private static final Instant HACKTOBER_LAST_DAY_MIDNIGHT = Instant.ofEpochSecond(HACKTOBER_LAST_DAY.toEpochDay() * 24 * 60 * 60);

    @Autowired
    private WorkingDayController workingDayController;

    @Autowired
    private TimesheetController timesheetController;

    @Test
    public void contextLoads() {
    }

    @Test
    public void addWorkingDay() {
        WorkingDayDto workingDay = createWorkingDay();

        WorkingDayDto addedWorkingDay = workingDayController.addWorkingDay(workingDay);

        assertThat(addedWorkingDay).isEqualToIgnoringGivenFields(workingDay, "id");
    }

    @Test
    public void roundtripForWorkingDay() {
        WorkingDayDto addedWorkingDay = workingDayController.addWorkingDay(createWorkingDay());

        assertThat(timesheetController.getTimesheetForYearMonth(HACKTOBER))
                .contains(summaryFor(addedWorkingDay));
    }

    @Test
    public void roundTripWithDate() {
        WorkingDayDto addedWorkingDay = workingDayController.addWorkingDay(createWorkingDay());

        assertThat(timesheetController.getTimesheetForMonthByDate(HACKTOBER_LAST_DAY))
                .contains(summaryFor(addedWorkingDay));
    }

    @Test
    public void updateWorkingDay() {
        WorkingDayDto addedWorkingDay = workingDayController.addWorkingDay(createWorkingDay());
        addedWorkingDay.setStart(HACKTOBER_LAST_DAY_MIDNIGHT.plus(Duration.ofHours(1)));

        workingDayController.updateWorkingDay(addedWorkingDay);

        WorkingDayDto persistedWorkingDay = workingDayController.getWorkingDay(addedWorkingDay.getId());
        assertThat(persistedWorkingDay).isEqualTo(addedWorkingDay);
    }

    @Test
    public void ensureBreaksGetUpdated() {
        Long workingDayId = workingDayController.addWorkingDay(createWorkingDayWithBreak()).getId();
        WorkingDayDto persistedWorkingDay = workingDayController.getWorkingDay(workingDayId);
        BreakDto persistedBreak = persistedWorkingDay.getBreaks().get(0);

        persistedBreak.setStart(persistedBreak.getStart().minus(30, MINUTES));
        persistedBreak.setDuration(persistedBreak.getDuration().plusMinutes(30));
        workingDayController.updateWorkingDay(persistedWorkingDay);

        WorkingDayDto updatedWorkingDay = workingDayController.getWorkingDay(workingDayId);

        assertThat(updatedWorkingDay.getBreaks()).contains(persistedBreak);
    }

    @Test
    public void ensureNewBreaksArePersistedDuringUpdate() {
        WorkingDayDto workingDay = workingDayController.addWorkingDay(createWorkingDay());

        BreakDto aBreak = createBreak();
        workingDay.setBreaks(singletonList(aBreak));
        workingDayController.updateWorkingDay(workingDay);

        assertThat(workingDay.getBreaks().get(0)).isEqualToIgnoringGivenFields(aBreak,"id");
    }

    private WorkingDayDto createWorkingDayWithBreak() {
        BreakDto newBreak = createBreak();
        WorkingDayDto workingDay = createWorkingDay();
        workingDay.setBreaks(singletonList(newBreak));
        return workingDay;
    }

    private BreakDto createBreak() {
        Instant breakStart = HACKTOBER_LAST_DAY_MIDNIGHT.plus(11 * 60 + 30, MINUTES);
        Instant breakEnd = HACKTOBER_LAST_DAY_MIDNIGHT.plus(12 * 60 + 15, MINUTES);
        return new BreakDto(null, breakStart, breakEnd, null);
    }

    private WorkingDayDto createWorkingDay() {
        Instant end = HACKTOBER_LAST_DAY_MIDNIGHT.plus(Duration.ofHours(8));
        return new WorkingDayDto(null, HACKTOBER_LAST_DAY, HACKTOBER_LAST_DAY_MIDNIGHT, end, "Test day", emptyList());
    }

    private WorkingDaySummaryDto summaryFor(WorkingDayDto workingDay) {
        return WorkingDaySummaryDto.toDto(workingDay.toEntity());
    }
}
