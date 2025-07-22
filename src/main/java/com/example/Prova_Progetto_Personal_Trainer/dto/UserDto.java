package com.example.Prova_Progetto_Personal_Trainer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDto {
    @NotEmpty(message = "Il nome non può essere vuoto")
    private String nome;

    @NotEmpty(message = "Il cognome non può essere vuoto")
    private String cognome;

    @NotEmpty(message = "Lo username non può essere vuoto")
    private String username;

    @NotEmpty(message = "La password non può essere vuota")
    private String password;

    @NotEmpty(message = "l'email non può essere vuota")
    @Email(message = "formato email non valido")
    private String email;


}
