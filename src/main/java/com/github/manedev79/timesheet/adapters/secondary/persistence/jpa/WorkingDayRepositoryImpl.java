package com.github.manedev79.timesheet.adapters.secondary.persistence.jpa;

import com.github.manedev79.timesheet.application.WorkingDayRepository;
import com.github.manedev79.timesheet.domain.WorkingDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class WorkingDayRepositoryImpl implements WorkingDayRepository {
    @Autowired
    private JpaWorkingDayRepository jpaRepo;

    @Override
    public Optional<WorkingDay> findByDay(LocalDate day) {

        return jpaRepo.findByDay(day).map(JpaWorkingDay::toDomainObject);
    }

    @Override
    public List<WorkingDay> findByDayBetween(LocalDate start, LocalDate end) {
        return jpaRepo.findByDayBetween(start, end)
                .stream()
                .map(JpaWorkingDay::toDomainObject)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkingDay> findAll() {
        return jpaRepo.findAll()
                .stream()
                .map(JpaWorkingDay::toDomainObject)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<WorkingDay> findById(Long id) {
        return jpaRepo.findById(id).map(JpaWorkingDay::toDomainObject);
    }

    @Override
    public WorkingDay saveAndFlush(WorkingDay workingDay) {
        JpaWorkingDay persistedWorkingDay = jpaRepo.saveAndFlush(JpaWorkingDay.fromDomainObject(workingDay));
        return persistedWorkingDay.toDomainObject();
    }

    @Override
    public void deleteAll() {
        jpaRepo.deleteAll();
    }
}
