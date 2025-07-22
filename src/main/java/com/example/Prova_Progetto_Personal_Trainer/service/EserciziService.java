package com.example.Prova_Progetto_Personal_Trainer.service;

import com.example.Prova_Progetto_Personal_Trainer.dto.EsercizioDto;
import com.example.Prova_Progetto_Personal_Trainer.exception.NotFoundException;
import com.example.Prova_Progetto_Personal_Trainer.model.Esercizio;
import com.example.Prova_Progetto_Personal_Trainer.model.Muscolo;
import com.example.Prova_Progetto_Personal_Trainer.repository.EsercizioRepository;
import com.example.Prova_Progetto_Personal_Trainer.repository.MuscoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EserciziService {

    @Autowired
    private EsercizioRepository esercizioRepository;

    @Autowired
    private MuscoloRepository muscoloRepository;

    public Esercizio saveEsercizio(EsercizioDto esercizioDto) throws NotFoundException {

        Muscolo muscolo = muscoloRepository.findById(esercizioDto.getMuscoloId())
                .orElseThrow(() -> new NotFoundException("Esercizio non trovato"));

        Esercizio esercizio = new Esercizio();
        esercizio.setNome(esercizioDto.getNome());
        esercizio.setDescrizione(esercizioDto.getDescrizione());
        esercizio.setMuscolo(muscolo);
        return esercizioRepository.save(esercizio);
    }

    public Page<Esercizio> getAllEsercizi(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return esercizioRepository.findAll(pageable);
    }


    public Esercizio getEsercizio(int id) throws NotFoundException {
        return esercizioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Esercizion con id" + id + "non trovato"));
    }

    public Esercizio updateEsercizi(int id, EsercizioDto esercizioDto) throws NotFoundException {
        Esercizio esercizioDaAggiornare =  getEsercizio(id);
        esercizioDaAggiornare.setNome(esercizioDto.getNome());
        esercizioDaAggiornare.setDescrizione(esercizioDto.getDescrizione());
        if(esercizioDaAggiornare.getMuscolo().getId()!=(esercizioDto.getMuscoloId())){
            Muscolo muscolo = muscoloRepository.findById(esercizioDto.getMuscoloId())
                    .orElseThrow(() -> new NotFoundException("Esercizio non trovato"));
            esercizioDaAggiornare.setMuscolo(muscolo);
        }

        return esercizioRepository.save(esercizioDaAggiornare);
    }

    public void deleteEsercizio(int id) throws NotFoundException {
        Esercizio esercizioDaCancellare = getEsercizio(id);
        esercizioRepository.delete(esercizioDaCancellare);
    }




}
