package com.github.manedev79.timesheet.application;

import com.github.manedev79.timesheet.domain.WorkingDay;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.github.manedev79.timesheet.application.WorkingDayDto.toDto;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class WorkingDayService {

    private final WorkingDayRepository workingDayRepository;

    public WorkingDayService(final WorkingDayRepository workingDayRepository) {
        this.workingDayRepository = workingDayRepository;
    }

    public List<WorkingDayDto> getAllWorkingDays() {
        return workingDayRepository.findAll().stream()
                .map(WorkingDayDto::toDto)
                .collect(toList());
    }

    public Optional<WorkingDayDto> getWorkingDay(final Long id) {
        return workingDayRepository.findById(id)
                .map(WorkingDayDto::toDto);
    }

    public Optional<WorkingDayDto> getWorkingDay(final LocalDate day) {
        return workingDayRepository.findByDay(day)
                .map(WorkingDayDto::toDto);
    }

    public WorkingDayDto addWorkingDay(final WorkingDayDto workingDay) {
        return toDto(workingDayRepository.saveAndFlush(workingDay.toEntity()));
    }

    public void updateWorkingDay(WorkingDayDto workingDay) {
        workingDayRepository.saveAndFlush(workingDay.toEntity());
    }
}
