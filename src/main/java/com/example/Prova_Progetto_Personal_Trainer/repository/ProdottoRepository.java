package com.example.Prova_Progetto_Personal_Trainer.repository;

import com.example.Prova_Progetto_Personal_Trainer.model.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdottoRepository extends JpaRepository<Prodotto,Integer> {
    List<Prodotto> findByNomeContainingIgnoreCase(String nome);
    List<Prodotto> findByPrezzoBetween(int min, int max);
}
