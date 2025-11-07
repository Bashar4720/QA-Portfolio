package com.example.trainer.Service;

import com.example.trainer.Respository.TraineeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.trainer.Model.Trainee;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TraineeService {

    private final TraineeRepository repo;

    public TraineeService(TraineeRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<Trainee> getAll() {
        return repo.findAll();
    }

    public Trainee create(Trainee t) {
        if (t.getName() == null || t.getName().isBlank()) {
            throw new IllegalArgumentException("Trainee name is required");
        }
        if (t.getEmail() == null || t.getEmail().isBlank()) {
            throw new IllegalArgumentException("Trainee email is required");
        }
        if (t.getJoinDate() == null) {
            t.setJoinDate(LocalDate.now());
        }
        return repo.save(t);
    }

    public Trainee assignPlan(Long traineeId, Long planId) {
        Trainee t = repo.findById(traineeId)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found: " + traineeId));
        t.setActivePlanId(planId);
        return repo.save(t);
    }

    public Trainee removePlan(Long traineeId) {
        Trainee t = repo.findById(traineeId)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found: " + traineeId));
        t.setActivePlanId(null);
        return repo.save(t);
    }

    @Transactional(readOnly = true)
    public List<Trainee> joinedAfter(LocalDate date) {
        return repo.findAll().stream()
                .filter(t -> t.getJoinDate() != null && t.getJoinDate().isAfter(date))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Map<Integer, Long> groupedByYearCount() {
        return repo.findAll().stream()
                .filter(t -> t.getJoinDate() != null)
                .collect(Collectors.groupingBy(
                        t -> t.getJoinDate().getYear(),
                        Collectors.counting()
                ));
    }
}
