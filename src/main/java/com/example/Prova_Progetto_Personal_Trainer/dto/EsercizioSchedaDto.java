package com.example.Prova_Progetto_Personal_Trainer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EsercizioSchedaDto {
    private String nomeEsercizio;
    private String descrizione;
    private String muscolo;
}
