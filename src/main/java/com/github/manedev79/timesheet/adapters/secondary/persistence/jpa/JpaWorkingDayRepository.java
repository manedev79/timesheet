package com.github.manedev79.timesheet.adapters.secondary.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaWorkingDayRepository extends JpaRepository<JpaWorkingDay, Long> {
    Optional<JpaWorkingDay> findByDay(final LocalDate day);

    List<JpaWorkingDay> findByDayBetween(final LocalDate start, final LocalDate end);
}
