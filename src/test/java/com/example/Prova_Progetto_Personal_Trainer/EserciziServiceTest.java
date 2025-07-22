package com.example.Prova_Progetto_Personal_Trainer;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import com.example.Prova_Progetto_Personal_Trainer.dto.EsercizioDto;
import com.example.Prova_Progetto_Personal_Trainer.model.Esercizio;
import com.example.Prova_Progetto_Personal_Trainer.model.Muscolo;
import com.example.Prova_Progetto_Personal_Trainer.repository.EsercizioRepository;
import com.example.Prova_Progetto_Personal_Trainer.repository.MuscoloRepository;
import com.example.Prova_Progetto_Personal_Trainer.service.EserciziService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EserciziServiceTest {



        @Mock
        private EsercizioRepository esercizioRepository;

        @Mock
        private MuscoloRepository muscoloRepository;

        @InjectMocks
        private EserciziService eserciziService;

        @Test
        public void testSaveEsercizio() throws Exception {
            EsercizioDto dto = new EsercizioDto();
            dto.setNome("Panca piana");
            dto.setDescrizione("Esercizio per il petto");
            dto.setMuscoloId(1);

            Muscolo muscolo = new Muscolo();
            muscolo.setId(1);
            muscolo.setNome("Petto");

            Esercizio entity = new Esercizio();
            entity.setNome(dto.getNome());
            entity.setDescrizione(dto.getDescrizione());
            entity.setMuscolo(muscolo);

            when(muscoloRepository.findById(1)).thenReturn(Optional.of(muscolo));
            when(esercizioRepository.save(any(Esercizio.class))).thenReturn(entity);

            Esercizio result = eserciziService.saveEsercizio(dto);

            assertNotNull(result);
            assertEquals("Panca piana", result.getNome());
            assertEquals("Esercizio per il petto", result.getDescrizione());
            assertEquals("Petto", result.getMuscolo().getNome());
        }

        @Test
        public void testUpdateEsercizio() throws Exception {
            Esercizio originale = new Esercizio();
            originale.setId(1);
            originale.setNome("Vecchio nome");
            originale.setDescrizione("Vecchia descrizione");

            Muscolo muscoloOriginale = new Muscolo();
            muscoloOriginale.setId(1);
            muscoloOriginale.setNome("Petto");
            originale.setMuscolo(muscoloOriginale);

            EsercizioDto aggiornato = new EsercizioDto();
            aggiornato.setNome("Nuovo nome");
            aggiornato.setDescrizione("Nuova descrizione");
            aggiornato.setMuscoloId(2);

            Muscolo nuovoMuscolo = new Muscolo();
            nuovoMuscolo.setId(2);
            nuovoMuscolo.setNome("Spalle");

            when(esercizioRepository.findById(1)).thenReturn(Optional.of(originale));
            when(muscoloRepository.findById(2)).thenReturn(Optional.of(nuovoMuscolo));
            when(esercizioRepository.save(any(Esercizio.class))).thenReturn(originale);

            Esercizio result = eserciziService.updateEsercizi(1, aggiornato);

            assertNotNull(result);
            assertEquals("Spalle", result.getMuscolo().getNome());
        }

        @Test
        public void testGetEsercizio() throws Exception {
            Esercizio esercizio = new Esercizio();
            esercizio.setId(1);
            esercizio.setNome("Panca piana");

            when(esercizioRepository.findById(1)).thenReturn(Optional.of(esercizio));

            Esercizio result = eserciziService.getEsercizio(1);

            assertNotNull(result);
            assertEquals("Panca piana", result.getNome());
        }

        @Test
        public void testDeleteEsercizio() throws Exception {
            Esercizio esercizio = new Esercizio();
            esercizio.setId(1);

            when(esercizioRepository.findById(1)).thenReturn(Optional.of(esercizio));

            assertDoesNotThrow(() -> eserciziService.deleteEsercizio(1));

            verify(esercizioRepository, times(1)).delete(esercizio);
        }
}
