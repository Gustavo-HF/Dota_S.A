package com.dota.partidas.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dota.partidas.model.Jogador;
import com.dota.partidas.service.JogadorService;


@WebMvcTest(JogadorController.class)
public class JogadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JogadorService jogadorService;

    private Jogador jogador;

    @BeforeEach
    void setup(){
        jogador = new Jogador();
        jogador.setId(1L);
        jogador.setNome("Felipe Astini");
        jogador.setNickname("Ast1ni");
        jogador.setNacionalidade("Brasileiro");
        jogador.setPosicao(3);
        jogador.setFuncao("Offlaner");
        jogador.setHeroisMaisJogados(List.of("Slardar, Timbersaw e UnderLord"));
        jogador.setMmr(12000);


   
    }

    @Test
    @DisplayName("Should return a player by Id")
    void testBuscarPorId() throws Exception {
        when(jogadorService.buscarPorId(1L)).thenReturn(jogador);

        mockMvc.perform(get("/jogadores/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Felipe Astini"))
                .andExpect(jsonPath("$.nickname").value("Ast1ni"))
                .andExpect(jsonPath("$.posicao").value(3))
                .andExpect(jsonPath("$.nacionalidade").value("Brasileiro"))
                .andExpect(jsonPath("$.funcao").value("Offlaner"))
                .andExpect(jsonPath("$.mmr").value(12000))
                .andExpect(jsonPath("$.heroisMaisJogados").value("Slardar, Timbersaw e UnderLord"));


    }

}
