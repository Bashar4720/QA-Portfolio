package com.example.trainer.Respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.trainer.Model.Trainee;

public interface TraineeRepository extends JpaRepository<Trainee, Long> {

}
