package com.example.trainer.controller;


import com.example.trainer.Service.TraineeService;
import org.springframework.web.bind.annotation.*;

import com.example.trainer.Model.Trainee;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trainees")
public class TraineController {

    private final TraineeService service;

    public TraineController(TraineeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Trainee> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Trainee create(@RequestBody Trainee t) {
        return service.create(t);
    }

    @PutMapping("/{traineeId}/assign-plan/{planId}")
    public Trainee assignPlan(@PathVariable Long traineeId, @PathVariable Long planId) {
        return service.assignPlan(traineeId, planId);
    }

    @PutMapping("/{traineeId}/remove-plan")
    public Trainee removePlan(@PathVariable Long traineeId) {
        return service.removePlan(traineeId);
    }

    @GetMapping("/joined-after/{date}")
    public List<Trainee> joinedAfter(@PathVariable String date) {
        LocalDate d = LocalDate.parse(date); 
        return service.joinedAfter(d);
    }

    @GetMapping("/grouped-by-year")
    public Map<Integer, Long> groupedByYear() {
        return service.groupedByYearCount();
    }
}

