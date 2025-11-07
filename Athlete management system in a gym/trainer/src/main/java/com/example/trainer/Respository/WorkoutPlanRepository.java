package com.example.trainer.Respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.trainer.Model.WorkoutPlan;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {

}
