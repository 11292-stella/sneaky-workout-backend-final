package com.example.Prova_Progetto_Personal_Trainer.repository;

import com.example.Prova_Progetto_Personal_Trainer.model.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedBackRepository extends JpaRepository<FeedBack,Integer> {
    List<FeedBack> findBySchedaId(int schedaId);
}
