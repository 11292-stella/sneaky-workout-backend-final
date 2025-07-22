package com.example.Prova_Progetto_Personal_Trainer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoceCarrelloDto {

    @NotEmpty(message = "Il prodotto deve avere un nome")
    private String nome;
    @NotEmpty(message = "Il prodotto deve avere una descrizione")
    private String descrizione;

    @NotNull(message = "Il prodotto deve essere assegnato")
    private Integer prodottoId;
    @NotNull(message = "Deve esserci il quantitativo di prodotti")
    private Integer quantita;
}
