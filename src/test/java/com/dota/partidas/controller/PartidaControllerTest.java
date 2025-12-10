package com.dota.partidas.controller;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dota.partidas.model.Campeonato;
import com.dota.partidas.model.Jogador;
import com.dota.partidas.model.JogadorPartida;
import com.dota.partidas.model.Partida;
import com.dota.partidas.model.Time;
import com.dota.partidas.model.compositkeys.JogadorPartidaId;
import com.dota.partidas.service.PartidaService;

@WebMvcTest(PartidaController.class)
public class PartidaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PartidaService partidaService;

    private Partida partida;

    @BeforeEach
    void setup() {

        // OBJETOS BÁSICOS — sem ciclos
        Campeonato campeonato = new Campeonato();
        campeonato.setId(10L);
        campeonato.setNome("The International");

        Jogador jogador = new Jogador();
        jogador.setId(5L);
        jogador.setNome("Faker");

        Time time = new Time();
        time.setId(7L);
        time.setNome("Team Radiant");

        // PARTIDA
        partida = new Partida();
        partida.setId(1L);
        partida.setPontuacao("2-1");
        partida.setDuracaoPartida(LocalTime.of(0, 45));
        partida.setData(LocalDate.of(2025, 9, 14));
        partida.setDiferencaPatrimonioEquipes(5000.00);
        partida.setPicks(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
        partida.setBans(List.of("10", "9", "8", "7", "6", "5", "4", "3", "2", "1"));
        partida.setCampeonato(campeonato);
        partida.setMvp(jogador);
        partida.setTimeA(time);
        partida.setTimeB(time);

        // JogadorPartida — sem ciclos
        JogadorPartida jp = new JogadorPartida();
        jp.setJogador(jogador);
        jp.setKda("10/2/8");
        jp.setPatrimonioLiquidoIndividual(15000);
        jp.setJogadorPartidaId(new JogadorPartidaId(jogador.getId(), partida.getId()));

        // adiciona o jogador à partida
        partida.setJogadores(List.of(jp));
    }

    @Test
    @DisplayName("Should get the statistics from the match")
    void testListar() throws Exception {

        // MOCK DO SERVICE
        when(partidaService.listarTodas()).thenReturn(List.of(partida));

        mockMvc.perform(get("/partidas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                // DADOS DA PARTIDA
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].pontuacao").value("2-1"))
                .andExpect(jsonPath("$[0].diferencaPatrimonioEquipes").value(5000.00))
                .andExpect(jsonPath("$[0].duracaoPartida").value("00:45:00"))
                .andExpect(jsonPath("$[0].data").value("2025-09-14"))

                // PICKS E BANS
                .andExpect(jsonPath("$[0].picks[0]").value("1"))
                .andExpect(jsonPath("$[0].picks[9]").value("10"))
                .andExpect(jsonPath("$[0].bans[0]").value("10"))
                .andExpect(jsonPath("$[0].bans[9]").value("1"))

                // CAMPEONATO
                .andExpect(jsonPath("$[0].campeonato.id").value(10))
                .andExpect(jsonPath("$[0].campeonato.nome").value("The International"))

                // MVP
                .andExpect(jsonPath("$[0].mvp.id").value(5))
                .andExpect(jsonPath("$[0].mvp.nome").value("Faker"))

                // TIMES
                .andExpect(jsonPath("$[0].timeA.id").value(7))
                .andExpect(jsonPath("$[0].timeA.nome").value("Team Radiant"))
                .andExpect(jsonPath("$[0].timeB.id").value(7))
                .andExpect(jsonPath("$[0].timeB.nome").value("Team Radiant"))

                // JOGADORES
                .andExpect(jsonPath("$[0].jogadores.length()").value(1))
                .andExpect(jsonPath("$[0].jogadores[0].jogador.id").value(5))
                .andExpect(jsonPath("$[0].jogadores[0].jogador.nome").value("Faker"))
                .andExpect(jsonPath("$[0].jogadores[0].kda").value("10/2/8"))
                .andExpect(jsonPath("$[0].jogadores[0].patrimonioLiquidoIndividual").value(15000));
    }

}
