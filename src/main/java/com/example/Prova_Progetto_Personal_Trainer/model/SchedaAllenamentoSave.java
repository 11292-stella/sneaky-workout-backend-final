package com.example.Prova_Progetto_Personal_Trainer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class SchedaAllenamentoSave {

    @Id
    @GeneratedValue
    private int id;

    private LocalDate dataCreazione;

    @ManyToOne
    private User utente;
    @Column
    private String nomeUtente;

    @Column(nullable = false)
    private boolean visibilePubblicamente = true;

    @OneToMany(mappedBy = "scheda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EsercizioScheda> esercizi;


}
