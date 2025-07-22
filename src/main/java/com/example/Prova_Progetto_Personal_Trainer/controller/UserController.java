package com.example.Prova_Progetto_Personal_Trainer.controller;


import com.example.Prova_Progetto_Personal_Trainer.dto.UserDto;
import com.example.Prova_Progetto_Personal_Trainer.exception.NotFoundException;
import com.example.Prova_Progetto_Personal_Trainer.exception.ValidationException;
import com.example.Prova_Progetto_Personal_Trainer.model.User;
import com.example.Prova_Progetto_Personal_Trainer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint per ottenere tutti gli utenti (tipicamente solo per ADMIN)
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

    // Endpoint per ottenere un singolo utente per ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    // Un utente può vedere il proprio profilo, un ADMIN può vedere qualsiasi profilo
    public User getUserById(@PathVariable int id, @AuthenticationPrincipal User authenticatedUser) throws NotFoundException {
        // Controllo che un utente non-ADMIN possa vedere solo il proprio profilo
        if (authenticatedUser.getId() != id && !authenticatedUser.getRole().name().equals("ADMIN")) {
            throw new NotFoundException("Non autorizzato a visualizzare questo profilo.");
        }
        return userService.getUser(id);
    }

    // Endpoint per aggiornare un utente per ID
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    // Un utente può aggiornare il proprio profilo, un ADMIN può aggiornare qualsiasi profilo
    public User updateUser(@PathVariable int id, @RequestBody @Validated UserDto userDto,
                           BindingResult bindingResult, @AuthenticationPrincipal User authenticatedUser) throws NotFoundException, ValidationException {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (s, e) -> s + e + " ");
            throw new ValidationException(errorMessage.trim());
        }

        // Controllo che un utente non-ADMIN possa aggiornare solo il proprio profilo
        if (authenticatedUser.getId() != id && !authenticatedUser.getRole().name().equals("ADMIN")) {
            throw new NotFoundException("Non autorizzato a modificare questo profilo.");
        }

        return userService.updateUser(id, userDto);
    }

    // Endpoint per eliminare un utente per ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    // Un utente può eliminare il proprio profilo, un ADMIN può eliminare qualsiasi profilo
    public String deleteUser(@PathVariable int id, @AuthenticationPrincipal User authenticatedUser) throws NotFoundException {
        // Controllo che un utente non-ADMIN possa eliminare solo il proprio profilo
        if (authenticatedUser.getId() != id && !authenticatedUser.getRole().name().equals("ADMIN")) {
            throw new NotFoundException("Non autorizzato ad eliminare questo profilo.");
        }

        userService.deleteUser(id);
        return "User with ID " + id + " deleted successfully";
    }


}
