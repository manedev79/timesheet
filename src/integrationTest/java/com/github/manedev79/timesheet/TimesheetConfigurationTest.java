package com.github.manedev79.timesheet;

import com.github.manedev79.timesheet.adapters.secondary.persistence.TimesheetConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimesheetConfigurationTest {
    @Autowired
    private TimesheetConfiguration configuration;

    @Test
    public void regurlarWorkingHours() {
        assertThat(configuration.getDailyWorkingHours()).isEqualTo(Duration.ofHours(8));
    }
}
