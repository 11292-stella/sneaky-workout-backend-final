package com.example.Prova_Progetto_Personal_Trainer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class EsercizioScheda {

    @Id
    @GeneratedValue
    private int id;

    private String nomeEsercizio;
    private String descrizione;

    @ManyToOne
    @JoinColumn(name = "muscolo_id")
    @JsonIgnoreProperties("esercizi")
    private Muscolo muscolo;

    @ManyToOne
    @JoinColumn(name = "scheda_id")
    private SchedaAllenamentoSave scheda;
}
