package com.example.trainer.Model;

import jakarta.persistence.*;

@Entity
public class WorkoutPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private int difficulty;  

    public WorkoutPlan() {}

    public WorkoutPlan(String name, String description, int difficulty) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public int getDifficulty() { return difficulty; }

    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }
}
