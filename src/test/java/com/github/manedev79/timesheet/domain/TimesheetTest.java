package com.github.manedev79.timesheet.domain;

import com.github.manedev79.timesheet.domain.fixtures.TestFixtures;
import org.junit.Test;

import java.time.Month;
import java.time.MonthDay;

import static org.assertj.core.api.Assertions.assertThat;

public class TimesheetTest {

    @Test
    public void containsAllDaysOfMonth() {
        assertThat(new Timesheet(TestFixtures.HACKTOBER).getWorkingDays()).hasSize(31);
    }

    @Test
    public void equalsByMonthOnly() {
        Timesheet timesheet = new Timesheet(TestFixtures.HACKTOBER);
        Timesheet timesheet2 = new Timesheet(TestFixtures.HACKTOBER);
        timesheet2.getWorkingDays().put(MonthDay.of(Month.OCTOBER, 31), new WorkingDay(TestFixtures.HACKTOBER_LAST_DAY_DATE));

        assertThat(timesheet2).isEqualTo(timesheet);
    }
}