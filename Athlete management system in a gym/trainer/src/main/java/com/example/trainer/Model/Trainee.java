package com.example.trainer.Model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Trainee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    private LocalDate joinDate;

    private Long activePlanId;

    public Trainee() {}

    public Trainee(String name, String email, LocalDate joinDate) {
        this.name = name;
        this.email = email;
        this.joinDate = joinDate;
    }


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public LocalDate getJoinDate() { return joinDate; }

    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }

    public Long getActivePlanId() { return activePlanId; }

    public void setActivePlanId(Long activePlanId) { this.activePlanId = activePlanId; }
}