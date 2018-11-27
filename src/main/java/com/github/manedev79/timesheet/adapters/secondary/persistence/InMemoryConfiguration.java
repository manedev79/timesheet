package com.github.manedev79.timesheet.adapters.secondary.persistence;

import com.github.manedev79.timesheet.domain.WorkingHoursConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class InMemoryConfiguration {

    @Bean
    public WorkingHoursConfiguration workingHoursConfiguration() {
        return () -> Duration.ofHours(8);
    }
}
