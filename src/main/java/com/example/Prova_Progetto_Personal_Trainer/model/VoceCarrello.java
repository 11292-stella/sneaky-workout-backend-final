package com.example.Prova_Progetto_Personal_Trainer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class VoceCarrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User utente;

    @ManyToOne
    private Prodotto prodotto;

    private int quantita;
}
