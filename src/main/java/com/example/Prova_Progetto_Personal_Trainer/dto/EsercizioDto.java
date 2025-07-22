package com.example.Prova_Progetto_Personal_Trainer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EsercizioDto {

    @NotEmpty(message = "Il nome dell'esercizio è obbligatorio")
    private String nome;

    @NotEmpty(message = "La descrizione dell'esercizio è obbligatoria")
    private String descrizione;

    @NotNull(message = "Devi associare un muscolo all'esercizio")
    private Integer muscoloId;
}
