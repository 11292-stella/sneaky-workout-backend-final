package com.example.Prova_Progetto_Personal_Trainer;

import com.example.Prova_Progetto_Personal_Trainer.dto.VoceCarrelloDto;
import com.example.Prova_Progetto_Personal_Trainer.model.Prodotto;
import com.example.Prova_Progetto_Personal_Trainer.model.User;
import com.example.Prova_Progetto_Personal_Trainer.model.VoceCarrello;
import com.example.Prova_Progetto_Personal_Trainer.repository.ProdottoRepository;
import com.example.Prova_Progetto_Personal_Trainer.repository.UserRepository;
import com.example.Prova_Progetto_Personal_Trainer.repository.VoceCarrelloRepository;
import com.example.Prova_Progetto_Personal_Trainer.service.ProdottoService;
import com.example.Prova_Progetto_Personal_Trainer.service.UserService;
import com.example.Prova_Progetto_Personal_Trainer.service.VoceCarrelloService;
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
public class VoceCarrelloServiceTest {

    @Mock
    private ProdottoRepository prodottoRepository;

    @Mock
    private VoceCarrelloRepository voceCarrelloRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private VoceCarrelloService voceCarrelloService;


    @Test
    public void testSaveVoceCarrelloConDto() throws Exception {
        VoceCarrelloDto voceCarrelloDto = new VoceCarrelloDto();
        voceCarrelloDto.setNome("Barrette");
        voceCarrelloDto.setDescrizione("Barrette al cioccolato");
        voceCarrelloDto.setQuantita(3);

        User user = new User();
        user.setId(1);
        user.setNome("Nome utente");

        Prodotto prodotto = new Prodotto();
        prodotto.setId(1);
        prodotto.setNome("Barrette");
        prodotto.setDescrizione("Barrette al cioccolato");

        VoceCarrello voceCarrello = new VoceCarrello();
        voceCarrello.setId(1);
        voceCarrello.setUtente(user);
        voceCarrello.setProdotto(prodotto);
        voceCarrello.setQuantita(voceCarrelloDto.getQuantita());

        when(prodottoRepository.findById(any())).thenReturn(Optional.of(prodotto));
        when(voceCarrelloRepository.save(any(VoceCarrello.class))).thenReturn(voceCarrello);

        VoceCarrello result = voceCarrelloService.saveVoceCarrello(voceCarrelloDto, user);

        assertNotNull(result);
        assertEquals(user, result.getUtente());
        assertEquals(prodotto,result.getProdotto());
        assertEquals(3, result.getQuantita());

    }

    @Test
    public void testGetVoceCarrello() throws Exception {

        VoceCarrello voceCarrello = new VoceCarrello();
        voceCarrello.setId(1);
        voceCarrello.setQuantita(3);

        when(voceCarrelloRepository.findById(1)).thenReturn(Optional.of(voceCarrello));

        VoceCarrello result = voceCarrelloService.getVoceCarrello(1);

        assertNotNull(result);
        assertEquals(3,result.getQuantita());

    }

    @Test
    public void testVoceCarrelloNotFound(){
        when(voceCarrelloRepository.findById(2)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class,
                () -> voceCarrelloService.getVoceCarrello(2));

        assertTrue(exception.getMessage().contains("non trovato"));
    }

    @Test
    public void testDeleteVoceCarrello() throws Exception{
        VoceCarrello voceCarrello = new VoceCarrello();
        voceCarrello.setId(1);
        voceCarrello.setQuantita(3);

        when(voceCarrelloRepository.findById(1)).thenReturn(Optional.of(voceCarrello));

        assertDoesNotThrow(() -> voceCarrelloService.deleteVoceCarrello(1));
        verify(voceCarrelloRepository, times(1)).delete(voceCarrello);
    }

}
