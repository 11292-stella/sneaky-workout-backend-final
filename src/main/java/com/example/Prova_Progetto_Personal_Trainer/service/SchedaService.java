package com.example.Prova_Progetto_Personal_Trainer.service;

import com.example.Prova_Progetto_Personal_Trainer.dto.EsercizioSchedaDto;
import com.example.Prova_Progetto_Personal_Trainer.model.Esercizio;
import com.example.Prova_Progetto_Personal_Trainer.model.Muscolo;
import com.example.Prova_Progetto_Personal_Trainer.repository.EsercizioRepository;
import com.example.Prova_Progetto_Personal_Trainer.repository.MuscoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchedaService {

    @Autowired
    private EsercizioRepository esercizioRepository;

    @Autowired
    private MuscoloRepository muscoloRepository;

    public List<EsercizioSchedaDto> generaScheda(List<Integer> muscoliId){
        List<EsercizioSchedaDto> scheda = new ArrayList<>();

        for(Integer muscoloId : muscoliId){
            Muscolo muscolo = muscoloRepository.findById(muscoloId).orElse(null);
            if(muscolo != null){
                List<Esercizio> esercizi = esercizioRepository.findByMuscoloId(muscoloId);
                esercizi.stream().limit(2).forEach(e -> scheda.add(new EsercizioSchedaDto(e.getNome(), e.getDescrizione(), muscolo.getNome())));
            }
        }
        return  scheda;
    }
}
