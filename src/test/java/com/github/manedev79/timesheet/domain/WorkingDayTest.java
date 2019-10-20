package com.github.manedev79.timesheet.domain;

import org.junit.Test;

import java.time.Duration;

import static com.github.manedev79.timesheet.domain.fixtures.TestFixtures.HACKTOBER_LAST_DAY_DATE;
import static org.assertj.core.api.Assertions.assertThat;

public class WorkingDayTest {

    @Test
    public void noBreaksDuration() {
        assertThat(new WorkingDay(HACKTOBER_LAST_DAY_DATE).getTotalBreaksDuration()).isEqualTo(Duration.ZERO);
    }

    @Test
    public void noWorkDuration() {
        assertThat(new WorkingDay(HACKTOBER_LAST_DAY_DATE).getTotalWorkDuration()).isEqualTo(Duration.ZERO);
    }
}
