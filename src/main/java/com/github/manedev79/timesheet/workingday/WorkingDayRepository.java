package com.github.manedev79.timesheet.workingday;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkingDayRepository extends JpaRepository<WorkingDay, Long> {

    Optional<WorkingDay> findByDay(final LocalDate day);

    List<WorkingDay> findByDayBetween(final LocalDate start, final LocalDate end);

}
