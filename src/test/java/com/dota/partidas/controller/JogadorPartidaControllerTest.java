package com.dota.partidas.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dota.partidas.model.Jogador;
import com.dota.partidas.model.JogadorPartida;
import com.dota.partidas.model.Partida;
import com.dota.partidas.model.Time;
import com.dota.partidas.model.compositkeys.JogadorPartidaId;
import com.dota.partidas.service.JogadorPartidaService;

public class JogadorPartidaControllerTest {

    @SpringBootTest
    @AutoConfigureMockMvc
    public class JogadorPartidaTest {

        @Autowired
        private MockMvc mockMvc;
        private Partida partida;
        private Jogador jogador;
        private JogadorPartida jogadorPartida;

        @MockitoBean
        //Mockagem do JogadorPartidaService
        private JogadorPartidaService jogadorPartidaService;

        @BeforeEach
        void setup() {

            // Criação do objeto Jogador
            jogador = new Jogador();
            jogador.setId(1L);
            jogador.setNome("Felipe Astini");
            jogador.setNickname("Ast1ni");
            jogador.setNacionalidade("Brasileiro");
            jogador.setPosicao(3);
            jogador.setFuncao("Offlaner");
            jogador.setHeroisMaisJogados(List.of("Slardar, Timbersaw e UnderLord"));
            jogador.setMmr(12000);

            // Criação do objeto JogadorPartida
            jogadorPartida = new JogadorPartida();
            jogadorPartida.setJogador(jogador);
            jogadorPartida.setKda("8/0/2");

            // Criação do objeto partida
            Partida partida = new Partida();
            partida.setId(1L);
            partida.setData(LocalDate.now());
            partida.setDuracaoPartida(LocalTime.of(0, 45));
            partida.setTimeA(new Time());
            partida.setTimeB(new Time());

            jogadorPartida.setPartida(partida);
        }

        @Test
        @DisplayName("Should update KDA of a player")
        void testAtualizar() throws Exception {

            // O KDA que deve ser atualizado
            String novoKDA = "10/05/18";

            // Cria uma instância existente de JogadorPartida e associa jogador e partida
            JogadorPartida existente = jogadorPartida;
            existente.setJogador(jogador);
            existente.setPartida(partida);

            // Configura o mock para retornar o objeto existente quando buscarPorId for chamado
            when(jogadorPartidaService.buscarPorId(any(JogadorPartidaId.class)))
                    .thenReturn(existente);

            // Cria uma instância atualizada de JogadorPartida com o novo KDA
            JogadorPartida atualizado = jogadorPartida;
            atualizado.setKda(novoKDA);

            // Configura o mock para retornar o objeto atualizado quando salvar for chamado
            when(jogadorPartidaService.salvar(any(JogadorPartida.class)))
                    .thenReturn(atualizado);

            // Executa a requisição PUT simulada para atualizar o KDA do jogador na partida
            mockMvc.perform(put("/jogador-partida/{idPartida}/{idJogador}", 1L, 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                            "kda": "10/05/18"
                        }
                        """))
                    // Verifica se o status da resposta é 200 OK
                    .andExpect(status().isOk())
                    // Verifica se o KDA retornado corresponde ao novo valor
                    .andExpect(jsonPath("$.kda").value(novoKDA))
                    // Verifica se os dados do jogador retornados estão corretos
                    .andExpect(jsonPath("$.jogador.id").value(1))
                    .andExpect(jsonPath("$.jogador.nome").value("Felipe Astini"))
                    .andExpect(jsonPath("$.jogador.nickname").value("Ast1ni"));
        }

    }
}
