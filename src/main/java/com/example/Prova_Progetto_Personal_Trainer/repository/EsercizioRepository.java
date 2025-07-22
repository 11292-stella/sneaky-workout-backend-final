package com.example.Prova_Progetto_Personal_Trainer.repository;

import com.example.Prova_Progetto_Personal_Trainer.model.Esercizio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EsercizioRepository extends JpaRepository<Esercizio,Integer> {
    List<Esercizio>findByMuscoloId(Integer muscoloId);
}
