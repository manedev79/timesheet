package com.github.manedev79.timesheet.application;

import com.github.manedev79.timesheet.domain.WorkingDay;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class TimesheetService {
    private final WorkingDayRepository workingDayRepository;

    public TimesheetService(WorkingDayRepository workingDayRepository) {
        this.workingDayRepository = workingDayRepository;
    }

    public List<WorkingDaySummaryDto> getWorkingDaysBetween(LocalDate start, LocalDate end) {
        return workingDayRepository.findByDayBetween(start, end).stream()
                .map(WorkingDaySummaryDto::toDto)
                .collect(toList());
    }
}
