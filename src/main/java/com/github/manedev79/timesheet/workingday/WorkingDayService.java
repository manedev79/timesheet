package com.github.manedev79.timesheet.workingday;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WorkingDayService {

    private final WorkingDayRepository workingDayRepository;

    public WorkingDayService(final WorkingDayRepository workingDayRepository) {
        this.workingDayRepository = workingDayRepository;
    }

    public List<WorkingDay> getAllWorkingDays() {
        return workingDayRepository.findAll();
    }

    public Optional<WorkingDay> getWorkingDay(final Long id) {
        return workingDayRepository.findById(id);
    }

    public Optional<WorkingDay> getWorkingDay(final LocalDate day) {
        return workingDayRepository.findByDay(day);
    }
}
