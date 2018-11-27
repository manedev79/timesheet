package com.github.manedev79.timesheet;

import com.github.manedev79.timesheet.adapters.primary.rest.TimesheetController;
import com.github.manedev79.timesheet.adapters.primary.rest.WorkingDayController;
import com.github.manedev79.timesheet.application.BreakDto;
import com.github.manedev79.timesheet.application.WorkingDayDto;
import com.github.manedev79.timesheet.application.WorkingDaySummaryDto;
import com.github.manedev79.timesheet.domain.FlexTimeDomainService;
import com.github.manedev79.timesheet.utils.TestFixtures;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.YearMonth;
import java.util.List;

import static com.github.manedev79.timesheet.utils.TestFixtures.createLongWorkingDay;
import static com.github.manedev79.timesheet.utils.TestFixtures.createWorkingDay;
import static java.time.Month.OCTOBER;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimesheetTest {
    private static final YearMonth HACKTOBER = YearMonth.of(2018, 10);
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
    public void updateWorkingDay() {
        WorkingDayDto addedWorkingDay = workingDayController.addWorkingDay(createWorkingDay());
        addedWorkingDay.setStart(TestFixtures.HACKTOBER_LAST_DAY_MIDNIGHT.plus(Duration.ofHours(1)));

        workingDayController.updateWorkingDay(addedWorkingDay);

        WorkingDayDto persistedWorkingDay = workingDayController.getWorkingDay(addedWorkingDay.getId());
        assertThat(persistedWorkingDay).isEqualTo(addedWorkingDay);
    }

    @Test
    public void ensureBreaksGetUpdated() {
        Long workingDayId = workingDayController.addWorkingDay(TestFixtures.createWorkingDayWithBreak()).getId();
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

        BreakDto aBreak = TestFixtures.createBreak();
        workingDay.setBreaks(singletonList(aBreak));
        workingDayController.updateWorkingDay(workingDay);

        assertThat(workingDay.getBreaks().get(0)).isEqualToIgnoringGivenFields(aBreak, "id");
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

        assertThat(timesheetController.getTimesheetForMonthByDate(TestFixtures.HACKTOBER_LAST_DAY))
                .contains(summaryFor(addedWorkingDay));
    }

    @Test
    public void containsAllDaysOfMonth() {
        assertThat(timesheetController.getTimesheetForYearMonth(YearMonth.of(2018, OCTOBER))).hasSize(31);
    }

    @Test
    public void calculateFlexTime() {
        workingDayController.addWorkingDay(createLongWorkingDay());

        List<WorkingDaySummaryDto> timesheet = timesheetController.getTimesheetForYearMonth(HACKTOBER);
        assertThat(timesheet.get(30).getFlexTime()).isEqualTo(Duration.ofHours(1));
    }

    private WorkingDaySummaryDto summaryFor(WorkingDayDto workingDay) {
        return WorkingDaySummaryDto.toDto(workingDay.toEntity());
    }
}
