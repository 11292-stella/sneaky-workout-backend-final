package com.example.Prova_Progetto_Personal_Trainer.controller;

import com.example.Prova_Progetto_Personal_Trainer.dto.FeedBackRequestDto;
import com.example.Prova_Progetto_Personal_Trainer.dto.FeedBackResponseDto;
import com.example.Prova_Progetto_Personal_Trainer.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedBackController {

    @Autowired
    private FeedBackService feedBackService;


    @PostMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<FeedBackResponseDto> lasciaFeedback(@RequestBody FeedBackRequestDto dto,
                                                              @RequestHeader("Authorization") String token) throws Exception {
        FeedBackResponseDto risposta = feedBackService.saveFeedback(dto, token);
        return ResponseEntity.ok(risposta);
    }


    @GetMapping("/scheda/{id}")
    public ResponseEntity<List<FeedBackResponseDto>> getFeedbackByScheda(@PathVariable int id) {
        List<FeedBackResponseDto> lista = feedBackService.getFeedbackBySchedaId(id);
        return ResponseEntity.ok(lista);
    }
}
