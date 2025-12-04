package com.dota.partidas.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.dota.partidas.model.Campeonato;
import com.dota.partidas.model.Jogador;
import com.dota.partidas.model.JogadorPartida;
import com.dota.partidas.model.Partida;
import com.dota.partidas.model.Time;
import com.dota.partidas.service.PartidaService;

@WebMvcTest(PartidaController.class)
public class PartidaControllerTest {
   
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PartidaService partidaService;

    private Partida partida;
    private Campeonato campeonato;
    private Jogador jogador;
    private JogadorPartida jogadorPartida;
    private Time time;
    
    @BeforeEach
    void setup(){
        partida = new Partida();
        partida.setBans(List.of("1, 2, 3, 4, 5, 6, 7, 8, 9, 10"));
        partida.setPicks(List.of("1, 2, 3, 4, 5, 6, 7 ,8, 9, 10,"));
        partida.setDiferencaPatrimonioEquipes(5000.00);
        partida.setDuracaoPartida(LocalTime.of(0,45));
        partida.setData(LocalDate.of(2025, 9, 14));
        partida.setPontuacao("2-1");
        partida.setId(1L);
        partida.setJogadores(List.of(jogadorPartida));
        partida.setCampeonato(campeonato);
        partida.setMvp(jogador);
        partida.setTimeB(time);
        partida.setTimeA(time);
    }
    
    @Test
    @DisplayName("Should get the statistics from the match")
    void testListar() {
        when(partidaService.listarTodas()).thenReturn(List.of(partida));

        mockMvc.perform(get("/partidas").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].pontuacao").value("2-1"))
                .andExpect(jsonPath("$[0].diferencaPatrimonioEquipes").value(5000.00));
    }

}
