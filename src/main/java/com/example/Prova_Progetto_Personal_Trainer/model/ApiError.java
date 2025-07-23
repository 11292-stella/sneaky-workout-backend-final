package com.example.Prova_Progetto_Personal_Trainer.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {



    private String message;
    private LocalDateTime dataErrore;
}
