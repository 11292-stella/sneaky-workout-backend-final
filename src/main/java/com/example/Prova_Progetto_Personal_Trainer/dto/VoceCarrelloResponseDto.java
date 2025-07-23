package com.example.Prova_Progetto_Personal_Trainer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoceCarrelloResponseDto {

    private int id;
    private String nomeProdotto;
    private String descrizioneProdotto;
    private int prezzoUnitario;
    private int quantita;
    private int totale;
    private int prodottoId;

}
