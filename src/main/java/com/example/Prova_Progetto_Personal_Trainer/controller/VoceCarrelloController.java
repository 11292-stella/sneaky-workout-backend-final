package com.example.Prova_Progetto_Personal_Trainer.controller;

import com.example.Prova_Progetto_Personal_Trainer.dto.ProdottoDto;
import com.example.Prova_Progetto_Personal_Trainer.dto.VoceCarrelloDto;
import com.example.Prova_Progetto_Personal_Trainer.dto.VoceCarrelloResponseDto;
import com.example.Prova_Progetto_Personal_Trainer.exception.NotFoundException;
import com.example.Prova_Progetto_Personal_Trainer.exception.ValidationException;
import com.example.Prova_Progetto_Personal_Trainer.model.Prodotto;
import com.example.Prova_Progetto_Personal_Trainer.model.User;
import com.example.Prova_Progetto_Personal_Trainer.model.VoceCarrello;
import com.example.Prova_Progetto_Personal_Trainer.service.ProdottoService;
import com.example.Prova_Progetto_Personal_Trainer.service.VoceCarrelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrello")
public class VoceCarrelloController {
    @Autowired
    private VoceCarrelloService voceCarrelloService;

    @PostMapping("")
    @PreAuthorize("hasAuthority('USER')")
    public VoceCarrello saveVoceCarrello(@RequestBody @Validated VoceCarrelloDto voceCarrelloDto,
                                     BindingResult bindingResult,
                                         @AuthenticationPrincipal User authenticatedUser) throws ValidationException, NotFoundException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (e, s) -> e + s + "; ").trim());
        }
        return voceCarrelloService.saveVoceCarrello(voceCarrelloDto, authenticatedUser);
    }

    @GetMapping("/{id}")
    public VoceCarrello getVoceCarrello(@PathVariable int id) throws NotFoundException {
        return voceCarrelloService.getVoceCarrello(id);
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Page<VoceCarrello> getAllVoceCarrello(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "id") String sortBy) {
        return voceCarrelloService.getAllVoceCarrello(page, size, sortBy);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public VoceCarrello updateVoceCarrello(@PathVariable int id,
                                   @RequestBody @Validated VoceCarrelloDto voceCarrelloDto,
                                   BindingResult bindingResult,
                                           @AuthenticationPrincipal User authenticatedUser) throws ValidationException, NotFoundException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (e, s) -> e + s + "; ").trim());
        }
        return voceCarrelloService.updateVoceCarrello(voceCarrelloDto, id,authenticatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteVoceCarrello(@PathVariable int id) throws NotFoundException {
        voceCarrelloService.deleteVoceCarrello(id);
    }



    @GetMapping("/mio-carrello")
    @PreAuthorize("hasAuthority('USER')")
    public List<VoceCarrelloResponseDto> getMioCarrello(@AuthenticationPrincipal User authenticatedUser) {
        return voceCarrelloService.getCarrelloPerUtente(authenticatedUser);
    }
}
