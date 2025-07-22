package com.example.Prova_Progetto_Personal_Trainer.service;

import com.example.Prova_Progetto_Personal_Trainer.dto.VoceCarrelloDto;
import com.example.Prova_Progetto_Personal_Trainer.dto.VoceCarrelloResponseDto;
import com.example.Prova_Progetto_Personal_Trainer.exception.NotFoundException;
import com.example.Prova_Progetto_Personal_Trainer.model.Prodotto;
import com.example.Prova_Progetto_Personal_Trainer.model.User;
import com.example.Prova_Progetto_Personal_Trainer.model.VoceCarrello;
import com.example.Prova_Progetto_Personal_Trainer.repository.ProdottoRepository;
import com.example.Prova_Progetto_Personal_Trainer.repository.VoceCarrelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoceCarrelloService {
    @Autowired
    private VoceCarrelloRepository voceCarrelloRepository;

    @Autowired
    private ProdottoRepository prodottoRepository;

    @Autowired
    private UserService userService;

    public VoceCarrello saveVoceCarrello(VoceCarrelloDto voceCarrelloDto, User authenticatedUser) throws NotFoundException {



        Prodotto prodotto = prodottoRepository.findById(voceCarrelloDto.getProdottoId())
                .orElseThrow(() -> new NotFoundException("Prodotto non trovato"));

        VoceCarrello voceCarrello = new VoceCarrello();
        voceCarrello.setUtente(authenticatedUser);
        voceCarrello.setProdotto(prodotto);
        voceCarrello.setQuantita(voceCarrelloDto.getQuantita());

        return voceCarrelloRepository.save(voceCarrello);

    }

    public VoceCarrello getVoceCarrello(int id) throws NotFoundException {
        return voceCarrelloRepository.findById(id).orElseThrow(() -> new NotFoundException("Voce carrelo con id " +id+ " non trovato"));
    }

    public Page<VoceCarrello> getAllVoceCarrello(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        return voceCarrelloRepository.findAll(pageable);
    }

    public VoceCarrello updateVoceCarrello(VoceCarrelloDto voceCarrelloDto, int id , User authenticatedUser) throws NotFoundException {



        Prodotto prodotto = prodottoRepository.findById(voceCarrelloDto.getProdottoId())
                .orElseThrow(() -> new NotFoundException("Prodotto non trovato"));

        VoceCarrello voceCarrelloDaAggiornare = getVoceCarrello(id);
        voceCarrelloDaAggiornare.setUtente(authenticatedUser);
        voceCarrelloDaAggiornare.setProdotto(prodotto);
        voceCarrelloDaAggiornare.setQuantita(voceCarrelloDto.getQuantita());
        return voceCarrelloRepository.save(voceCarrelloDaAggiornare);


    }

    public void deleteVoceCarrello(int id) throws NotFoundException {
        VoceCarrello voceCarrelloDaCancellare = getVoceCarrello(id);
        voceCarrelloRepository.delete(voceCarrelloDaCancellare);
    }


    public List<VoceCarrelloResponseDto> getCarrelloPerUtente(User utente) {
        List<VoceCarrello> voci = voceCarrelloRepository.findByUtente(utente);

        return voci.stream().map(voce -> {
            Prodotto prodotto = voce.getProdotto();
            return new VoceCarrelloResponseDto(
                    prodotto.getNome(),
                    prodotto.getDescrizione(),
                    prodotto.getPrezzo(),
                    voce.getQuantita(),
                    prodotto.getPrezzo() * voce.getQuantita()
            );
        }).collect(Collectors.toList());
    }





}
