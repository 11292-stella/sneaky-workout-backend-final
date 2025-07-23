package com.example.Prova_Progetto_Personal_Trainer.controller;

import com.example.Prova_Progetto_Personal_Trainer.dto.MuscoloDto;
import com.example.Prova_Progetto_Personal_Trainer.exception.NotFoundException;
import com.example.Prova_Progetto_Personal_Trainer.exception.ValidationException;
import com.example.Prova_Progetto_Personal_Trainer.model.Muscolo;

import com.example.Prova_Progetto_Personal_Trainer.service.MuscoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/muscoli")
public class MuscoloController {

    @Autowired
    private MuscoloService muscoloService;

    @PostMapping("")
    @PreAuthorize("hasAuthority('USER')")
    public Muscolo saveMuscolo(@RequestBody @Validated MuscoloDto muscoloDto,
                               BindingResult bindingResult
                               ) throws ValidationException {

        if(bindingResult.hasErrors()){
            throw  new ValidationException(bindingResult.getAllErrors()
                    .stream().map(objectError -> objectError.getDefaultMessage())
                    .reduce("",(e,s)->e+s));
        }
        return muscoloService.saveMuscolo(muscoloDto);

    }

    @GetMapping("/{id}")
    public Muscolo getMuscolo(@PathVariable int id) throws NotFoundException {
        return muscoloService.getMuscolo(id);
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') ")
    public Page<Muscolo> getAllMuscolo(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "28") int size,
                                       @RequestParam(defaultValue = "id") String sortBy){
        return  muscoloService.getAllMuscolo(page,size,sortBy);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public Muscolo updateMuscolo(@PathVariable int id, @RequestBody @Validated MuscoloDto muscoloDto,BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw  new ValidationException(bindingResult.getAllErrors()
                    .stream().map(objectError -> objectError.getDefaultMessage())
                    .reduce("",(e,s)->e+s));
        }
        return muscoloService.updateMuscolo(id,muscoloDto);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteMuscolo(@PathVariable int id) throws NotFoundException {
        muscoloService.deleteMuscolo(id);
    }
}
