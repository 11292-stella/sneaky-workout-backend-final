package com.example.Prova_Progetto_Personal_Trainer.controller;

import com.example.Prova_Progetto_Personal_Trainer.dto.EsercizioSchedaDto;
import com.example.Prova_Progetto_Personal_Trainer.dto.SchedaRequestDto;
import com.example.Prova_Progetto_Personal_Trainer.service.SchedaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/scheda")
public class SchedaController {

    @Autowired
    private SchedaService schedaService;

    @PostMapping("")
    public List<EsercizioSchedaDto> creaScheda(@RequestBody SchedaRequestDto richiesta) {
        return schedaService.generaScheda(richiesta.getMuscoliId());
    }
}
