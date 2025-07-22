package com.example.Prova_Progetto_Personal_Trainer.service;

import com.example.Prova_Progetto_Personal_Trainer.dto.MuscoloDto;
import com.example.Prova_Progetto_Personal_Trainer.exception.NotFoundException;
import com.example.Prova_Progetto_Personal_Trainer.model.Muscolo;
import com.example.Prova_Progetto_Personal_Trainer.model.User;
import com.example.Prova_Progetto_Personal_Trainer.repository.MuscoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MuscoloService {
    @Autowired
    private MuscoloRepository muscoloRepository;

    public Muscolo saveMuscolo(MuscoloDto muscoloDto){

        Muscolo muscolo = new Muscolo();
        muscolo.setNome(muscoloDto.getNome());
        muscolo.setDescrizione(muscoloDto.getDescrizione());
        return muscoloRepository.save(muscolo);
    }

    public Page<Muscolo> getAllMuscolo(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return muscoloRepository.findAll(pageable);
    }

    public Muscolo getMuscolo(int id) throws NotFoundException {
        return muscoloRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Muscolo con id" + id + "non trovato"));
    }

    public Muscolo updateMuscolo(int id, MuscoloDto muscoloDto) throws NotFoundException {
        Muscolo muscoloDaAggiornare = getMuscolo(id);

        muscoloDaAggiornare.setNome(muscoloDto.getNome());
        muscoloDaAggiornare.setDescrizione(muscoloDto.getDescrizione());

        return muscoloRepository.save(muscoloDaAggiornare);
    }

    public void deleteMuscolo(int id) throws NotFoundException {
        Muscolo muscoloDaEliminare = getMuscolo(id);
        muscoloRepository.delete(muscoloDaEliminare);

    }
}
