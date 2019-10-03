package com.github.manedev79.timesheet.domain;

import com.github.manedev79.timesheet.domain.fixtures.TestFixtures;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TimesheetTest {

    @Test
    public void containsAllDaysOfMonth() {
        assertThat(new Timesheet(TestFixtures.HACKTOBER).getWorkingDays()).hasSize(31);
    }
}