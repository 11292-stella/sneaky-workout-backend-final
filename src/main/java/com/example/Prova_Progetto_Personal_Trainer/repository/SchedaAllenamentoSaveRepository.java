package com.example.Prova_Progetto_Personal_Trainer.repository;

import com.example.Prova_Progetto_Personal_Trainer.model.SchedaAllenamentoSave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchedaAllenamentoSaveRepository extends JpaRepository<SchedaAllenamentoSave,Integer> {
    List<SchedaAllenamentoSave> findByUtenteId(int id);

    List<SchedaAllenamentoSave> findByVisibilePubblicamenteTrue();
}
