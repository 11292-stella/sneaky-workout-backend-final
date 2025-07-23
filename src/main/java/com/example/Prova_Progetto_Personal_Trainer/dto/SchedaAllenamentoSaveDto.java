package com.example.Prova_Progetto_Personal_Trainer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor

public class SchedaAllenamentoSaveDto {

    private int id;
    private String dataCreazione;
    private String nomeUtente;
    private List<EsercizioSchedaDto> esercizi;
    private boolean visibilePubblicamente;

    public SchedaAllenamentoSaveDto(
            int id,
            String dataCreazione,
            List<EsercizioSchedaDto> esercizi,
            String nomeUtente,
            boolean visibilePubblicamente
    ) {
        this.id = id;
        this.dataCreazione = dataCreazione;
        this.esercizi = esercizi;
        this.nomeUtente = nomeUtente;
        this.visibilePubblicamente = visibilePubblicamente;
    }}
