package com.example.Prova_Progetto_Personal_Trainer.controller;

import com.example.Prova_Progetto_Personal_Trainer.dto.ProdottoDto;
import com.example.Prova_Progetto_Personal_Trainer.exception.NotFoundException;
import com.example.Prova_Progetto_Personal_Trainer.exception.ValidationException;
import com.example.Prova_Progetto_Personal_Trainer.model.Prodotto;
import com.example.Prova_Progetto_Personal_Trainer.service.ProdottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/prodotti")
public class ProdottoController {
    @Autowired
    private ProdottoService prodottoService;

    @PostMapping("")
    @PreAuthorize("hasAuthority('USER')")
    public Prodotto saveProdotto(@RequestBody @Validated ProdottoDto prodottoDto,
                                 BindingResult bindingResult) throws ValidationException, NotFoundException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (e, s) -> e + s + "; ").trim());
        }
        return prodottoService.saveProdotto(prodottoDto);
    }

    @GetMapping("/{id}")
    public Prodotto getProdotto(@PathVariable int id) throws NotFoundException {
        return prodottoService.getProdotto(id);
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Page<Prodotto> getAllProdotti(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "23") int size,
                                         @RequestParam(defaultValue = "id") String sortBy) {
        return prodottoService.getAllProdotti(page, size, sortBy);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public Prodotto updateProdotto(@PathVariable int id,
                                     @RequestBody @Validated ProdottoDto prodottoDto,
                                     BindingResult bindingResult) throws ValidationException, NotFoundException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (e, s) -> e + s + "; ").trim());
        }
        return prodottoService.updateProdotto(id, prodottoDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteProdotto(@PathVariable int id) throws NotFoundException {
        prodottoService.deleteProdotto(id);
    }
}
