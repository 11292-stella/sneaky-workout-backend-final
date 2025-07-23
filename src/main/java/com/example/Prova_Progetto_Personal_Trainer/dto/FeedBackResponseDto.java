package com.example.Prova_Progetto_Personal_Trainer.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedBackResponseDto {
    private String commento;
    private Integer voto;
    private String autore;
    private LocalDateTime data;

}
