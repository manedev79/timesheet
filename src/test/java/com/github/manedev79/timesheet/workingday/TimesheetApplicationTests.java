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

import static java.util.Collections.emptyList;
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

    private WorkingDayDto createWorkingDay() {
        Instant end = HACKTOBER_LAST_DAY_MIDNIGHT.plus(Duration.ofHours(8));
        return new WorkingDayDto(null, HACKTOBER_LAST_DAY, HACKTOBER_LAST_DAY_MIDNIGHT, end, "Test day", emptyList());
    }

    private WorkingDaySummaryDto summaryFor(WorkingDayDto workingDay) {
        return WorkingDaySummaryDto.toDto(workingDay.toEntity());
    }
}
