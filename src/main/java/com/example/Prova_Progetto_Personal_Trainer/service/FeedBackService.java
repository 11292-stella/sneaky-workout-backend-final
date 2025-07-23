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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public void saveFeedback(String feedbackText) { // Adatta i parametri
        // String token = ... // Se estraevi il token qui, non serve più
        // User user = jwtTool.getUserFromToken(token); // Questa riga causava l'errore

        // Ottieni l'oggetto Authentication dal SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User currentUser = (User) authentication.getPrincipal();
            System.out.println("DEBUG_FEEDBACK: Utente autenticato: " + currentUser.getUsername());
            // Ora puoi usare currentUser.getId(), currentUser.getUsername(), ecc.
            // Esempio: feedBackRepository.save(new Feedback(feedbackText, currentUser.getId()));
        } else {
            System.out.println("DEBUG_FEEDBACK: Utente non autenticato o tipo errato.");
            // Gestisci il caso in cui l'utente non è autenticato o l'oggetto principal non è un User
        }
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

