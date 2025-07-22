package com.example.Prova_Progetto_Personal_Trainer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Muscolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String descrizione;

    @OneToMany(mappedBy = "muscolo")
    @JsonIgnore
    private List<Esercizio> esercizi;

}
