package com.github.manedev79.timesheet;

import com.github.manedev79.timesheet.adapters.primary.rest.TimesheetController;
import com.github.manedev79.timesheet.application.BreakDto;
import com.github.manedev79.timesheet.application.TimesheetDto;
import com.github.manedev79.timesheet.application.WorkingDayDto;
import com.github.manedev79.timesheet.domain.Timesheet;
import com.github.manedev79.timesheet.fixtures.TestFixtures;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.Instant;
import java.time.MonthDay;
import java.util.Collections;
import java.util.List;

import static com.github.manedev79.timesheet.fixtures.TestFixtures.*;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimesheetTest {

    @Autowired
    private TimesheetController timesheetController;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void setup() {
        mongoTemplate.dropCollection(Timesheet.class);
    }

    @Test
    public void createTimesheet() {
        Timesheet timesheet = timesheetController.createTimesheet(TestFixtures.HACKTOBER);

        assertNotNull(timesheet);
    }

    @Test
    public void createWorkingDay() {
        List<BreakDto> breaks = Collections.singletonList(createBreak());

        WorkingDayDto dto = new WorkingDayDto(
                HACKTOBER_LAST_DAY_DATE,
                HACKTOBER_LAST_DAY_MIDNIGHT,
                HACKTOBER_LAST_DAY_MIDNIGHT.plus(8, HOURS).plus(45, MINUTES),
                Duration.ZERO,
                "Hacktober last day",
                breaks);

        timesheetController.updateWorkingDay(dto);

        TimesheetDto timesheet = timesheetController.getTimesheet(TestFixtures.HACKTOBER);
        assertEquals(dto, timesheet.getWorkingDays().get(MonthDay.from(HACKTOBER_LAST_DAY_DATE)));
    }

    @Test
    public void findTimesheet() {
        timesheetController.createTimesheet(TestFixtures.HACKTOBER);

        TimesheetDto timesheet = timesheetController.getTimesheet(TestFixtures.HACKTOBER);

        assertEquals(TestFixtures.HACKTOBER, timesheet.getYearMonth());
    }

    @Test
    public void updateWorkingDay() {
        WorkingDayDto workingDay = TestFixtures.createWorkingDay();
        timesheetController.updateWorkingDay(workingDay);

        WorkingDayDto updatedWorkingDay = TestFixtures.createWorkingDay();
        Instant newStartTime = HACKTOBER_LAST_DAY_MIDNIGHT.minus(Duration.ofHours(1));
        updatedWorkingDay.setStart(newStartTime);

        timesheetController.updateWorkingDay(updatedWorkingDay);

        WorkingDayDto persistedWorkingDay = timesheetController.getTimesheet(TestFixtures.HACKTOBER).getWorkingDays().get(HACKTOBER_LAST_DAY);
        assertThat(persistedWorkingDay).isEqualToIgnoringGivenFields(updatedWorkingDay, "flextime");
        assertThat(persistedWorkingDay.getFlextime()).isEqualTo(Duration.of(1, HOURS));
    }

    @Test
    public void roundtripForWorkingDay() {
        timesheetController.updateWorkingDay(TestFixtures.createWorkingDay());

        assertThat(timesheetController.getTimesheet(TestFixtures.HACKTOBER).getWorkingDay(HACKTOBER_LAST_DAY))
                .isEqualTo(TestFixtures.createWorkingDay());
    }

    @Test
    public void calculateFlexTime() {
        timesheetController.updateWorkingDay(createLongWorkingDay());

        TimesheetDto timesheet = timesheetController.getTimesheet(TestFixtures.HACKTOBER);
        assertThat(timesheet.getWorkingDay(MonthDay.from(HACKTOBER_LAST_DAY)).getFlextime()).isEqualTo(Duration.ofHours(1));
    }

}
