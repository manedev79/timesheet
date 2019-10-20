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
        BreakDto aBreak = createBreak();
        List<BreakDto> breaks = Collections.singletonList(aBreak);
        Duration totalWorkDuration = Duration.ofHours(8);

        WorkingDayDto dto = WorkingDayDto.builder()
                                         .day(HACKTOBER_LAST_DAY_DATE)
                                         .start(HACKTOBER_LAST_DAY_MIDNIGHT)
                                         .end(HACKTOBER_LAST_DAY_MIDNIGHT.plus(totalWorkDuration).plus(aBreak.getDuration()))
                                         .description("Hacktober last day")
                                         .breaks(breaks)
                                         .totalWork(totalWorkDuration)
                                         .totalBreak(aBreak.getDuration())
                                         .build();

        timesheetController.updateWorkingDay(HACKTOBER_STRING, dto);

        TimesheetDto timesheet = timesheetController.getTimesheet(HACKTOBER_STRING);
        assertEquals(dto, timesheet.getWorkingDays().get(HACKTOBER_LAST_DAY_DATE.getDayOfMonth() - 1));
    }

    @Test
    public void findTimesheet() {
        timesheetController.createTimesheet(TestFixtures.HACKTOBER);

        TimesheetDto timesheet = timesheetController.getTimesheet(HACKTOBER_STRING);

        assertEquals(TestFixtures.HACKTOBER, timesheet.getYearMonth());
    }

    @Test
    public void updateWorkingDay() {
        WorkingDayDto workingDay = TestFixtures.createWorkingDay();
        timesheetController.updateWorkingDay(HACKTOBER_STRING, workingDay);

        WorkingDayDto updatedWorkingDay = TestFixtures.createWorkingDay();
        Instant newStartTime = HACKTOBER_LAST_DAY_MIDNIGHT.minus(Duration.ofHours(1));
        updatedWorkingDay.setStart(newStartTime);

        timesheetController.updateWorkingDay(HACKTOBER_STRING, updatedWorkingDay);

        WorkingDayDto persistedWorkingDay = timesheetController.getTimesheet(HACKTOBER_STRING).getWorkingDays().get(HACKTOBER_LAST_DAY.getDayOfMonth() - 1);
        assertThat(persistedWorkingDay).isEqualToIgnoringGivenFields(updatedWorkingDay, "flextime", "totalBreak", "totalWork");
        assertThat(persistedWorkingDay.getFlextime()).isEqualTo(Duration.of(1, HOURS));
    }

    @Test
    public void roundtripForWorkingDay() {
        timesheetController.updateWorkingDay(HACKTOBER_STRING, TestFixtures.createWorkingDay());

        assertThat(timesheetController.getTimesheet(HACKTOBER_STRING).getWorkingDay(HACKTOBER_LAST_DAY))
                .isEqualTo(TestFixtures.createWorkingDay());
    }

    @Test
    public void calculateFlexTime() {
        timesheetController.updateWorkingDay(HACKTOBER_STRING, createLongWorkingDay());

        TimesheetDto timesheet = timesheetController.getTimesheet(HACKTOBER_STRING);
        assertThat(timesheet.getWorkingDay(MonthDay.from(HACKTOBER_LAST_DAY)).getFlextime()).isEqualTo(Duration.ofHours(1));
    }

}
