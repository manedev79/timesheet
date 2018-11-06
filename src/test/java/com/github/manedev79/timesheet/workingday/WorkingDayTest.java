package com.github.manedev79.timesheet.workingday;

import org.junit.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkingDayTest {

    @Test
    public void noBreaksDuration() {
        assertThat(new WorkingDay().getTotalBreaksDuration()).isEqualTo(Duration.ZERO);
    }
}
