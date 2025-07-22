package com.example.Prova_Progetto_Personal_Trainer.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {

    //i dati che vogliamo mostrare al client
    // quando c'è un errore

    private String message;
    private LocalDateTime dataErrore;
}
