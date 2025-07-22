package com.example.Prova_Progetto_Personal_Trainer.repository;


import com.example.Prova_Progetto_Personal_Trainer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    public Optional<User> findByUsername(String username);
}
