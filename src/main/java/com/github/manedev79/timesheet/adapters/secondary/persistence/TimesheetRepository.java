package com.github.manedev79.timesheet.adapters.secondary.persistence;

import com.github.manedev79.timesheet.domain.Timesheet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.YearMonth;
import java.util.Optional;

public interface TimesheetRepository extends MongoRepository<Timesheet, YearMonth> {
    Optional<Timesheet> findByYearMonth(YearMonth yearMonth);
}
