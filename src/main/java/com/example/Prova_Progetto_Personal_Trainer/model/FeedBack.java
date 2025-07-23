package com.example.Prova_Progetto_Personal_Trainer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String commento;

    private Integer voto;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private User utente;

    @ManyToOne
    @JoinColumn(name = "scheda_id")
    private SchedaAllenamentoSave scheda;

    private LocalDateTime dataInserimento = LocalDateTime.now();

}
