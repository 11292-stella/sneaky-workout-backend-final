package com.example.Prova_Progetto_Personal_Trainer.service;


import com.example.Prova_Progetto_Personal_Trainer.dto.LoginDto;
import com.example.Prova_Progetto_Personal_Trainer.exception.NotFoundException;
import com.example.Prova_Progetto_Personal_Trainer.model.User;
import com.example.Prova_Progetto_Personal_Trainer.repository.UserRepository;
import com.example.Prova_Progetto_Personal_Trainer.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(LoginDto loginDto) throws NotFoundException {
        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new NotFoundException("Credenziali non valide. Utente non trovato o password errata."));

        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {

            return jwtTool.createToken(user);
        } else {
            throw new NotFoundException("Credenziali non valide. Utente non trovato o password errata.");
        }
    }
}
