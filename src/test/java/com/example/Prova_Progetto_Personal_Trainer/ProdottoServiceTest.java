package com.example.Prova_Progetto_Personal_Trainer;

import com.example.Prova_Progetto_Personal_Trainer.dto.ProdottoDto;
import com.example.Prova_Progetto_Personal_Trainer.model.Prodotto;
import com.example.Prova_Progetto_Personal_Trainer.repository.ProdottoRepository;
import com.example.Prova_Progetto_Personal_Trainer.service.ProdottoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdottoServiceTest {

    @Mock
    private ProdottoRepository prodottoRepository;

    @InjectMocks
    private ProdottoService prodottoService;

    @Test
    public void testSaveProdottoConDto() throws Exception {

        ProdottoDto prodottoDto = new ProdottoDto();
        prodottoDto.setNome("Whey Protein");
        prodottoDto.setDescrizione("Barretta proteica");
        prodottoDto.setPrezzo(22);

        Prodotto prodotto = new Prodotto();
        prodotto.setId(1);
        prodotto.setNome(prodottoDto.getNome());
        prodotto.setDescrizione(prodottoDto.getDescrizione());
        prodotto.setPrezzo(prodottoDto.getPrezzo());

        when(prodottoRepository.save(any(Prodotto.class))).thenReturn(prodotto);

        Prodotto result = prodottoService.saveProdotto(prodottoDto);

        assertNotNull(result);
        assertEquals("Whey Protein", result.getNome());
        assertEquals("Barretta proteica", result.getDescrizione());
        assertEquals(22, result.getPrezzo());

    }

    @Test
    public void testGetEsempio() throws Exception {
        Prodotto prodotto = new Prodotto();
        prodotto.setId(1);
        prodotto.setNome("Whey Protein");
        prodotto.setDescrizione("Barretta proteica");
        prodotto.setPrezzo(22);

        when(prodottoRepository.findById(1)).thenReturn(Optional.of(prodotto));

        Prodotto result = prodottoService.getProdotto(1);

        assertNotNull(result);
        assertEquals("Whey Protein", result.getNome());
    }


    @Test
    public void testGetEsempioNotFound(){
        when(prodottoRepository.findById(2)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class,
                () -> prodottoService.getProdotto(2));

        assertTrue(exception.getMessage().contains("non trovato"));
    }

   @Test
    public void testDeleteProdotto() throws Exception {
        Prodotto prodotto = new Prodotto();
        prodotto.setId(1);
        prodotto.setNome("Whey Protein");

        when(prodottoRepository.findById(1)).thenReturn(Optional.of(prodotto));

        assertDoesNotThrow(() -> prodottoService.deleteProdotto(1));
        verify(prodottoRepository, times(1)).delete(prodotto);
   }

}
