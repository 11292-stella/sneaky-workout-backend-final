package com.example.Prova_Progetto_Personal_Trainer.controller;

import com.example.Prova_Progetto_Personal_Trainer.dto.EsercizioDto;
import com.example.Prova_Progetto_Personal_Trainer.dto.MuscoloDto;
import com.example.Prova_Progetto_Personal_Trainer.exception.NotFoundException;
import com.example.Prova_Progetto_Personal_Trainer.exception.ValidationException;
import com.example.Prova_Progetto_Personal_Trainer.model.Esercizio;
import com.example.Prova_Progetto_Personal_Trainer.model.Muscolo;
import com.example.Prova_Progetto_Personal_Trainer.service.EserciziService;
import com.example.Prova_Progetto_Personal_Trainer.service.MuscoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/esercizi")
public class EsercizioController {
    @Autowired
    private EserciziService eserciziService;

    @PostMapping("")
    @PreAuthorize("hasAuthority('USER')")
    public Esercizio saveEsercizio(@RequestBody @Validated EsercizioDto esercizioDto,
                                 BindingResult bindingResult
    ) throws ValidationException, NotFoundException {

        if(bindingResult.hasErrors()){
            throw  new ValidationException(bindingResult.getAllErrors()
                    .stream().map(objectError -> objectError.getDefaultMessage())
                    .reduce("",(e,s)->e+s));
        }
        return eserciziService.saveEsercizio(esercizioDto);

    }

    @GetMapping("/{id}")
    public Esercizio getEsercizio(@PathVariable int id) throws NotFoundException {
        return eserciziService.getEsercizio(id);
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') ")
    public Page<Esercizio> getAllEsercizio(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sortBy){
        return  eserciziService.getAllEsercizi(page,size,sortBy);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public Esercizio updateEsercizi(@PathVariable int id, @RequestBody @Validated EsercizioDto esercizioDto,BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw  new ValidationException(bindingResult.getAllErrors()
                    .stream().map(objectError -> objectError.getDefaultMessage())
                    .reduce("",(e,s)->e+s));
        }
        return eserciziService.updateEsercizi(id,esercizioDto);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteEsercizio(@PathVariable int id) throws NotFoundException {
        eserciziService.deleteEsercizio(id);
    }
}


