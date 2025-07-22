package com.example.Prova_Progetto_Personal_Trainer;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import com.example.Prova_Progetto_Personal_Trainer.dto.MuscoloDto;
import com.example.Prova_Progetto_Personal_Trainer.model.Muscolo;
import com.example.Prova_Progetto_Personal_Trainer.repository.MuscoloRepository;
import com.example.Prova_Progetto_Personal_Trainer.service.MuscoloService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MuscoloServiceTest {
    @Mock
    private MuscoloRepository muscoloRepository;

    @InjectMocks
    private MuscoloService muscoloService;

    @Test
    public void testSaveMuscoloConDto() {

        MuscoloDto muscoloDto = new MuscoloDto();
        muscoloDto.setNome("Tricipite");
        muscoloDto.setDescrizione("Muscolo braccio posteriore");


        Muscolo muscoloEntity = new Muscolo();
        muscoloEntity.setNome(muscoloDto.getNome());
        muscoloEntity.setDescrizione(muscoloDto.getDescrizione());


        when(muscoloRepository.save(any(Muscolo.class))).thenReturn(muscoloEntity);


        Muscolo result = muscoloService.saveMuscolo(muscoloDto);


        assertNotNull(result);
        assertEquals("Tricipite", result.getNome());
        assertEquals("Muscolo braccio posteriore", result.getDescrizione());
    }

    @Test
    public void testGetMuscoloSuccess() throws Exception {
        Muscolo muscolo = new Muscolo();
        muscolo.setId(1);
        muscolo.setNome("Bicipite");
        muscolo.setDescrizione("Muscolo braccio");

        when(muscoloRepository.findById(1)).thenReturn(Optional.of(muscolo));

        Muscolo result = muscoloService.getMuscolo(1);

        assertNotNull(result);
        assertEquals("Bicipite", result.getNome());
    }

    @Test
    public void testGetMuscoloNotFound() {
        when(muscoloRepository.findById(2)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            muscoloService.getMuscolo(2);
        });

        assertTrue(exception.getMessage().contains("non trovato"));
    }

    @Test
    public void testUpdateMuscolo() throws Exception {

        Muscolo muscoloOriginale = new Muscolo();
        muscoloOriginale.setId(1);
        muscoloOriginale.setNome("Deltoide");
        muscoloOriginale.setDescrizione("Spalla");


        MuscoloDto muscoloDtoAggiornato = new MuscoloDto();
        muscoloDtoAggiornato.setNome("Deltoide Modificato");
        muscoloDtoAggiornato.setDescrizione("Spalla aggiornata");


        Muscolo muscoloAggiornatoEntity = new Muscolo();
        muscoloAggiornatoEntity.setId(1);
        muscoloAggiornatoEntity.setNome(muscoloDtoAggiornato.getNome());
        muscoloAggiornatoEntity.setDescrizione(muscoloDtoAggiornato.getDescrizione());


        when(muscoloRepository.findById(1)).thenReturn(Optional.of(muscoloOriginale));


        when(muscoloRepository.save(any(Muscolo.class))).thenReturn(muscoloAggiornatoEntity);


        Muscolo result = muscoloService.updateMuscolo(1, muscoloDtoAggiornato);


        assertNotNull(result);
        assertEquals("Deltoide Modificato", result.getNome());
        assertEquals("Spalla aggiornata", result.getDescrizione());
    }

    @Test
    public void testDeleteMuscolo() throws Exception {
        Muscolo muscolo = new Muscolo();
        muscolo.setId(1);
        muscolo.setNome("Pettorale");

        when(muscoloRepository.findById(1)).thenReturn(Optional.of(muscolo));


        assertDoesNotThrow(() -> muscoloService.deleteMuscolo(1));

        verify(muscoloRepository, times(1)).delete(muscolo);
    }
}
