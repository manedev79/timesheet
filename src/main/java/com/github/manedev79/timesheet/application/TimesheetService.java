package com.github.manedev79.timesheet.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@Transactional
public class TimesheetService {
    private final WorkingDayRepository workingDayRepository;

    public TimesheetService(WorkingDayRepository workingDayRepository) {
        this.workingDayRepository = workingDayRepository;
    }

    public List<WorkingDaySummaryDto> getWorkingDaysBetween(final LocalDate start, final LocalDate end) {
        List<WorkingDaySummaryDto> allWorkingDays = getEmptyWorkingDaysBetween(start, end);

        workingDayRepository.findByDayBetween(start, end).stream()
                .map(WorkingDaySummaryDto::toDto)
                .forEach(replaceEmptyWorkingDay(allWorkingDays));

        return allWorkingDays;
    }

    private Consumer<WorkingDaySummaryDto> replaceEmptyWorkingDay(List<WorkingDaySummaryDto> allWorkingDays) {
        return dto -> allWorkingDays.set(dto.getDay().getDayOfMonth() - 1, dto);
    }

    private List<WorkingDaySummaryDto> getEmptyWorkingDaysBetween(LocalDate start, LocalDate end) {
        List<WorkingDaySummaryDto> allWorkingDays = new ArrayList<>();
        LocalDate currentDay = start;

        while (currentDay.isBefore(end) || currentDay.isEqual(end)) {
            allWorkingDays.add(WorkingDaySummaryDto.emptyWorkingDay(currentDay));
            currentDay = currentDay.plusDays(1);
        }
        return allWorkingDays;
    }
}
