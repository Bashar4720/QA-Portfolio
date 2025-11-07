package com.example.trainer.controller;

import com.example.trainer.Service.WorkoutPlanService;
import org.springframework.web.bind.annotation.*;

import com.example.trainer.Model.WorkoutPlan;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class WorkoutPlaneController {

    private final WorkoutPlanService Service;

    public WorkoutPlaneController(WorkoutPlanService service) {
        this.Service = service;
    }

    @GetMapping
    public List<WorkoutPlan> getAll() {
        return Service.getAll();
    }

    @PostMapping
    public WorkoutPlan create(@RequestBody WorkoutPlan wp) {
        return Service.create(wp);
    }
}
