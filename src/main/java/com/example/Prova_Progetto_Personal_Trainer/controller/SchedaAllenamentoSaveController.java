package com.example.Prova_Progetto_Personal_Trainer.controller;

import com.example.Prova_Progetto_Personal_Trainer.dto.SchedaAllenamentoSaveDto;
import com.example.Prova_Progetto_Personal_Trainer.exception.NotFoundException;
import com.example.Prova_Progetto_Personal_Trainer.security.JwtTool;
import com.example.Prova_Progetto_Personal_Trainer.service.SchedaAllenamentoSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/save")
@PreAuthorize("hasAuthority('USER')")
public class SchedaAllenamentoSaveController {

    @Autowired
    private SchedaAllenamentoSaveService schedaAllenamentoSaveService;

    @Autowired
    private JwtTool jwtTool;


    @PostMapping("")
    @PreAuthorize("hasAuthority('USER')")
    public SchedaAllenamentoSaveDto saveScheda(@RequestBody SchedaAllenamentoSaveDto dto,
                                               @RequestHeader("Authorization") String token) throws NotFoundException {
        return schedaAllenamentoSaveService.saveSchedaCompleta(dto, token);
    }

    @GetMapping("/schede")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<SchedaAllenamentoSaveDto>> getSchede(@RequestHeader("Authorization") String token) {
        try {
            List<SchedaAllenamentoSaveDto> schede = schedaAllenamentoSaveService.getSchedeUtente(token);
            return ResponseEntity.ok(schede);
        } catch (NotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> deleteScheda(@PathVariable int id, @RequestHeader("Authorization") String token) {
        try {
            schedaAllenamentoSaveService.deleteScheda(id, token);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }


    @GetMapping("/schede/pubbliche")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<SchedaAllenamentoSaveDto>> getSchedePubbliche() {
        List<SchedaAllenamentoSaveDto> pubbliche = schedaAllenamentoSaveService.getSchedePubbliche();
        return ResponseEntity.ok(pubbliche);
    }
}
