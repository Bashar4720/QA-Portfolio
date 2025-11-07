package com.example.trainer.Service;

import com.example.trainer.Respository.WorkoutPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.trainer.Model.WorkoutPlan;

import java.util.List;

@Service
@Transactional
public class WorkoutPlanService {

    private final WorkoutPlanRepository repo;

    public WorkoutPlanService(WorkoutPlanRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<WorkoutPlan> getAll() {
        return repo.findAll();
    }

    public WorkoutPlan create(WorkoutPlan wp) {
        if (wp.getName() == null || wp.getName().isBlank()) {
            throw new IllegalArgumentException("WorkoutPlan name is required");
        }
        if (wp.getDifficulty() < 1 || wp.getDifficulty() > 5) {
            throw new IllegalArgumentException("Difficulty must be between 1 and 5");
        }
        return repo.save(wp);
    }

    @Transactional(readOnly = true)
    public WorkoutPlan getOrThrow(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("WorkoutPlan not found: " + id));
    }
}
