package com.example.Prova_Progetto_Personal_Trainer.service;

import com.example.Prova_Progetto_Personal_Trainer.dto.EsercizioSchedaDto;
import com.example.Prova_Progetto_Personal_Trainer.dto.SchedaAllenamentoSaveDto;
import com.example.Prova_Progetto_Personal_Trainer.exception.NotFoundException;
import com.example.Prova_Progetto_Personal_Trainer.model.EsercizioScheda;
import com.example.Prova_Progetto_Personal_Trainer.model.SchedaAllenamentoSave;
import com.example.Prova_Progetto_Personal_Trainer.model.User;
import com.example.Prova_Progetto_Personal_Trainer.repository.MuscoloRepository;
import com.example.Prova_Progetto_Personal_Trainer.repository.SchedaAllenamentoSaveRepository;
import com.example.Prova_Progetto_Personal_Trainer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchedaAllenamentoSaveService {

    @Autowired
    private SchedaAllenamentoSaveRepository schedaRepo;

    @Autowired
    private MuscoloRepository muscoloRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtTool jwtTool;

    public List<EsercizioSchedaDto> saveScheda(SchedaAllenamentoSaveDto dto, String token) throws NotFoundException {
        User utente = jwtTool.getUserFromToken(token.substring(7));

        SchedaAllenamentoSave nuovaScheda = new SchedaAllenamentoSave();
        nuovaScheda.setUtente(utente);
        nuovaScheda.setNomeUtente(utente.getNome());
        nuovaScheda.setDataCreazione(LocalDate.now());
        nuovaScheda.setVisibilePubblicamente(dto.isVisibilePubblicamente());

        List<EsercizioScheda> esercizi = dto.getEsercizi().stream().map(esDto -> {
            EsercizioScheda esercizio = new EsercizioScheda();
            esercizio.setNomeEsercizio(esDto.getNomeEsercizio());
            esercizio.setDescrizione(esDto.getDescrizione());
            esercizio.setMuscolo(muscoloRepo.findByNome(esDto.getMuscolo()));
            esercizio.setScheda(nuovaScheda);
            return esercizio;
        }).toList();

        nuovaScheda.setEsercizi(esercizi);
       schedaRepo.save(nuovaScheda);

       return esercizi.stream().map(esercizioScheda -> new EsercizioSchedaDto(
               esercizioScheda.getNomeEsercizio(),
               esercizioScheda.getDescrizione(),
               esercizioScheda.getMuscolo().getNome()
       )).toList();
    }

    public List<SchedaAllenamentoSaveDto> getSchedeUtente(String token) throws NotFoundException {
        User utente = jwtTool.getUserFromToken(token.substring(7));
        List<SchedaAllenamentoSave> schede = schedaRepo.findByUtenteId(utente.getId());
        if (schede.isEmpty()) {
            return List.of();
        }
        return schede.stream().map(scheda ->
                new SchedaAllenamentoSaveDto(
                        scheda.getId(),
                        scheda.getDataCreazione().toString(),
                        scheda.getEsercizi().stream().map(es -> new EsercizioSchedaDto(
                                es.getNomeEsercizio(),
                                es.getDescrizione(),
                                es.getMuscolo() != null ? es.getMuscolo().getNome() : "Muscolo non definito"
                        )).toList(),
                        scheda.getNomeUtente(),
                        scheda.isVisibilePubblicamente()
                )
        ).toList();
    }

    public SchedaAllenamentoSaveDto saveSchedaCompleta(SchedaAllenamentoSaveDto dto, String token) throws NotFoundException {
        User utente = jwtTool.getUserFromToken(token.substring(7));

        SchedaAllenamentoSave nuovaScheda = new SchedaAllenamentoSave();
        nuovaScheda.setUtente(utente);
        nuovaScheda.setNomeUtente(utente.getNome());
        nuovaScheda.setDataCreazione(LocalDate.now());
        nuovaScheda.setVisibilePubblicamente(dto.isVisibilePubblicamente());

        List<EsercizioScheda> esercizi = dto.getEsercizi().stream().map(esDto -> {
            EsercizioScheda esercizio = new EsercizioScheda();
            esercizio.setNomeEsercizio(esDto.getNomeEsercizio());
            esercizio.setDescrizione(esDto.getDescrizione());
            esercizio.setMuscolo(muscoloRepo.findByNome(esDto.getMuscolo()));
            esercizio.setScheda(nuovaScheda);
            return esercizio;
        }).toList();

        nuovaScheda.setEsercizi(esercizi);
        schedaRepo.save(nuovaScheda);


        return convertToDto(nuovaScheda);
    }

    public SchedaAllenamentoSave getScheda(int id) throws NotFoundException {
        return schedaRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Scheda con id " + id + " non trovata"));
    }

    public void deleteScheda(int id, String token) throws NotFoundException, AccessDeniedException {
        User richiedente = jwtTool.getUserFromToken(token.substring(7));
        SchedaAllenamentoSave scheda = getScheda(id);

        System.out.println(" Richiesta di eliminazione ricevuta per scheda ID: " + id);
        System.out.println("Utente richiedente ID: " + richiedente.getId());
        System.out.println("Scheda creata da ID: " + (scheda.getUtente() != null ? scheda.getUtente().getId() : "null"));

        if (scheda.getUtente() == null || scheda.getUtente().getId() != richiedente.getId()) {
            System.out.println("Eliminazione bloccata: la scheda non appartiene all'utente.");
            throw new AccessDeniedException("Non puoi eliminare una scheda che non ti appartiene.");
        }

        schedaRepo.delete(scheda);
        System.out.println("Scheda eliminata correttamente dal database.");
    }

    public List<SchedaAllenamentoSave> getSchedeUtente(User utente) {
        return schedaRepo.findByUtenteId(utente.getId());
    }

    public SchedaAllenamentoSaveDto convertToDto(SchedaAllenamentoSave scheda) {
        return new SchedaAllenamentoSaveDto(
                scheda.getId(),
                scheda.getDataCreazione().toString(),
                scheda.getEsercizi().stream().map(es -> new EsercizioSchedaDto(
                        es.getNomeEsercizio(),
                        es.getDescrizione(),
                        es.getMuscolo() != null ? es.getMuscolo().getNome() : "Muscolo non definito"
                )).toList(),
                scheda.getNomeUtente(),
                scheda.isVisibilePubblicamente()
        );
    }


    public List<SchedaAllenamentoSaveDto> getSchedePubbliche() {
        List<SchedaAllenamentoSave> lista = schedaRepo.findByVisibilePubblicamenteTrue();
        return lista.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
