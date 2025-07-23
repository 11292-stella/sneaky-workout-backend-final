package com.example.Prova_Progetto_Personal_Trainer.service;

import com.example.Prova_Progetto_Personal_Trainer.dto.ProdottoDto;
import com.example.Prova_Progetto_Personal_Trainer.exception.NotFoundException;
import com.example.Prova_Progetto_Personal_Trainer.model.Prodotto;
import com.example.Prova_Progetto_Personal_Trainer.repository.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdottoService {

    @Autowired
    private ProdottoRepository prodottoRepository;

    public Prodotto saveProdotto(ProdottoDto prodottoDto){

        Prodotto prodotto = new Prodotto();
        prodotto.setNome(prodottoDto.getNome());
        prodotto.setDescrizione(prodottoDto.getDescrizione());
        prodotto.setPrezzo(prodottoDto.getPrezzo());
        return prodottoRepository.save(prodotto);

    }


    public Prodotto getProdotto(int id) throws NotFoundException {
      return  prodottoRepository.findById(id).orElseThrow(() -> new NotFoundException("Prodotto con id " + id + " non trovato"));

    }

    public Page<Prodotto> getAllProdotti(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        return prodottoRepository.findAll(pageable);
    }

    public Prodotto updateProdotto(int id,ProdottoDto prodottoDto) throws NotFoundException {
        Prodotto prodottoDaAggiornare = getProdotto(id);
        prodottoDaAggiornare.setNome(prodottoDto.getNome());
        prodottoDaAggiornare.setDescrizione(prodottoDto.getDescrizione());
        prodottoDaAggiornare.setPrezzo(prodottoDto.getPrezzo());
        return prodottoRepository.save(prodottoDaAggiornare);
    }

    public void deleteProdotto(int id) throws NotFoundException {
        Prodotto prodottoDaCancellare = getProdotto(id);
        prodottoRepository.delete(prodottoDaCancellare);

}
    public List<Prodotto> cercaPerNome(String nome) {
        return prodottoRepository.findByNomeContainingIgnoreCase(nome);
    }
}
