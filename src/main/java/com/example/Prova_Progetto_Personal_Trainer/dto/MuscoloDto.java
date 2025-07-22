package com.example.Prova_Progetto_Personal_Trainer.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MuscoloDto {

    @NotEmpty(message = "Il nome del muscolo è obbligatorio")
    private String nome;

    @NotEmpty(message = "La descrizione è obbligatoria")
    private String descrizione;
}
