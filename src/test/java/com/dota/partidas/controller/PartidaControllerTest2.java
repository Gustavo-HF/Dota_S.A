package com.dota.partidas.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dota.partidas.model.Campeonato;
import com.dota.partidas.model.Jogador;
import com.dota.partidas.model.Partida;
import com.dota.partidas.model.Time;
import com.dota.partidas.service.PartidaService;

@SpringBootTest
@AutoConfigureMockMvc
public class PartidaControllerTest2 {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PartidaService partidaService;

    @Test
    void testSalvar() throws Exception {
        // JSON enviado pelo cliente
        String partidaJson = """
            {
                "diferencaPatrimonioEquipes": 15000,
                "data": "2025-02-01",
                "pontuacao": "2x1",
                "picks": ["hero1","hero2","hero3","hero4","hero5","hero6","hero7","hero8","hero9","hero10"],
                "bans": ["ban1","ban2","ban3","ban4","ban5"],
                "mvp": { "id": 10 },
                "duracaoPartida": "01:15:20",
                "campeonato": { "id": 3 },
                "timeA": { "id": 1 },
                "timeB": { "id": 2 }
            }
            """;

        // Mock do retorno salvo do service
        Partida partidaSalva = new Partida();
        partidaSalva.setId(100L);
        partidaSalva.setDiferencaPatrimonioEquipes(15000);
        partidaSalva.setData(LocalDate.of(2025, 2, 1));
        partidaSalva.setPontuacao("2x1");
        partidaSalva.setPicks(List.of("hero1","hero2","hero3","hero4","hero5","hero6","hero7","hero8","hero9","hero10"));
        partidaSalva.setBans(List.of("ban1","ban2","ban3","ban4","ban5"));
        partidaSalva.setDuracaoPartida(LocalTime.of(1, 15, 20));

        Jogador mvp = new Jogador();
        mvp.setId(10L);
        partidaSalva.setMvp(mvp);

        Campeonato camp = new Campeonato();
        camp.setId(3L);
        partidaSalva.setCampeonato(camp);

        Time timeA = new Time();
        timeA.setId(1L);
        partidaSalva.setTimeA(timeA);

        Time timeB = new Time();
        timeB.setId(2L);
        partidaSalva.setTimeB(timeB);

        Mockito.when(partidaService.salvar(Mockito.any(Partida.class)))
                .thenReturn(partidaSalva);

        mockMvc.perform(post("/partidas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(partidaJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.pontuacao").value("2x1"))
                .andExpect(jsonPath("$.picks.length()").value(10))
                .andExpect(jsonPath("$.bans.length()").value(5))
                .andExpect(jsonPath("$.mvp.id").value(10))
                .andExpect(jsonPath("$.timeA.id").value(1))
                .andExpect(jsonPath("$.timeB.id").value(2));

        Mockito.verify(partidaService, Mockito.times(1))
                .salvar(Mockito.any(Partida.class));
    }
}
