package com.example.Prova_Progetto_Personal_Trainer.controller;


import com.example.Prova_Progetto_Personal_Trainer.dto.LoginDto;
import com.example.Prova_Progetto_Personal_Trainer.dto.UserDto;
import com.example.Prova_Progetto_Personal_Trainer.exception.NotFoundException;
import com.example.Prova_Progetto_Personal_Trainer.exception.ValidationException;
import com.example.Prova_Progetto_Personal_Trainer.model.User;
import com.example.Prova_Progetto_Personal_Trainer.service.AuthService;
import com.example.Prova_Progetto_Personal_Trainer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody @Validated UserDto userDto, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (s, e) -> s + e + " ");
            throw new ValidationException(errorMessage.trim());
        }
        return userService.saveUser(userDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Validated LoginDto loginDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (s, e) -> s + e + " ");
            throw new ValidationException(errorMessage.trim());
        }
        return authService.login(loginDto);
    }
}
