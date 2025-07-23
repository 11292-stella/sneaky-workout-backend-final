package com.example.Prova_Progetto_Personal_Trainer.repository;

import com.example.Prova_Progetto_Personal_Trainer.model.User;
import com.example.Prova_Progetto_Personal_Trainer.model.VoceCarrello;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoceCarrelloRepository extends JpaRepository<VoceCarrello,Integer> {

    List<VoceCarrello> findByProdottoId(Integer prodottoId);
    List<VoceCarrello> findByUtente(User utente);
    Optional<VoceCarrello> findByIdAndUtente(int id, User utente);
}
