package com.example.Prova_Progetto_Personal_Trainer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProdottoDto {

    @NotEmpty(message = "Il nome del prodotto deve essere scritto")
    private String nome;
    @NotEmpty(message = "Il prodotto deve essere descritto")
    private String descrizione;
    @NotNull(message = "Il prodotto deve avere un prezzo")
    private Integer prezzo;
}
