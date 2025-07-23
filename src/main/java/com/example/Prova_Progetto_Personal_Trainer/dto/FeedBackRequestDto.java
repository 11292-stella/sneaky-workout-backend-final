package com.example.Prova_Progetto_Personal_Trainer.dto;

import lombok.Data;

@Data
public class FeedBackRequestDto {
    private String commento;
    private Integer voto;
    private int schedaId;

}
