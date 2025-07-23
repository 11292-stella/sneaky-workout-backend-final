package com.example.Prova_Progetto_Personal_Trainer.service;

import com.example.Prova_Progetto_Personal_Trainer.dto.FeedBackRequestDto;
import com.example.Prova_Progetto_Personal_Trainer.dto.FeedBackResponseDto;
import com.example.Prova_Progetto_Personal_Trainer.exception.NotFoundException;
import com.example.Prova_Progetto_Personal_Trainer.model.FeedBack;
import com.example.Prova_Progetto_Personal_Trainer.model.SchedaAllenamentoSave;
import com.example.Prova_Progetto_Personal_Trainer.model.User;
import com.example.Prova_Progetto_Personal_Trainer.repository.FeedBackRepository;
import com.example.Prova_Progetto_Personal_Trainer.repository.SchedaAllenamentoSaveRepository;
import com.example.Prova_Progetto_Personal_Trainer.repository.UserRepository;
import com.example.Prova_Progetto_Personal_Trainer.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedBackService {
    @Autowired
    private FeedBackRepository feedBackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SchedaAllenamentoSaveRepository schedaAllenamentoSaveRepository;

    @Autowired
    private JwtTool jwtTool;

    public FeedBackResponseDto saveFeedback(FeedBackRequestDto dto, String token) throws NotFoundException {
        User utente = jwtTool.getUserFromToken(token.substring(7));

        SchedaAllenamentoSave scheda = schedaAllenamentoSaveRepository.findById(dto.getSchedaId())
                .orElseThrow(() -> new NotFoundException("Scheda con id " + dto.getSchedaId() + " non trovata"));

        FeedBack feedBack = new FeedBack();
        feedBack.setCommento(dto.getCommento());
        feedBack.setVoto(dto.getVoto());
        feedBack.setUtente(utente);
        feedBack.setScheda(scheda);
        feedBack.setDataInserimento(LocalDateTime.now());

        FeedBack saved = feedBackRepository.save(feedBack);
        return toResponseDto(saved);

    }

    public List<FeedBackResponseDto> getFeedbackBySchedaId(int schedaId) {
        List<FeedBack> feedbacks = feedBackRepository.findBySchedaId(schedaId);
        return feedbacks.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    private FeedBackResponseDto toResponseDto(FeedBack feedback) {
        FeedBackResponseDto dto = new FeedBackResponseDto();
        dto.setCommento(feedback.getCommento());
        dto.setVoto(feedback.getVoto());
        dto.setAutore(feedback.getUtente() != null ? feedback.getUtente().getUsername() : "Sconosciuto");
        dto.setData(feedback.getDataInserimento());
        return dto;
    }
}

