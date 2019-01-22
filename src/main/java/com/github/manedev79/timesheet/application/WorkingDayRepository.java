package com.github.manedev79.timesheet.application;

import com.github.manedev79.timesheet.domain.WorkingDay;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;

public interface WorkingDayRepository {

    Optional<WorkingDay> findByDay(final LocalDate day);

    List<WorkingDay> findByDayBetween(final LocalDate start, final LocalDate end);

    List<WorkingDay> findAll();

    Optional<WorkingDay> findById(Long id);

    WorkingDay saveAndFlush(WorkingDay workingDay);

    void deleteAll();
}
