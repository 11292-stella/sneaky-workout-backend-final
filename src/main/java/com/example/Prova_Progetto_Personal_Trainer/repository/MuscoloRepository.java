package com.example.Prova_Progetto_Personal_Trainer.repository;

import com.example.Prova_Progetto_Personal_Trainer.model.Muscolo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MuscoloRepository extends JpaRepository<Muscolo,Integer> {


    List<Muscolo> findByNomeContainingIgnoreCase(String nome);

    Muscolo findByNome(String muscolo);
}
