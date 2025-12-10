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
    //Mock do jogadorService e jogador
    private JogadorService jogadorService;

    private Jogador jogador;

    @BeforeEach
    void setup() {
        // Criação do objeto jogador antes de cada teste
        jogador = new Jogador();
        jogador.setId(1L);                          // Define o ID do jogador
        jogador.setNome("Felipe Astini");           // Define o nome
        jogador.setNickname("Ast1ni");              // Define o nickname
        jogador.setNacionalidade("Brasileiro");     // Define a nacionalidade
        jogador.setPosicao(3);                      // Define a posição (provavelmente no jogo)
        jogador.setFuncao("Offlaner");              // Define a função do jogador
        jogador.setHeroisMaisJogados(List.of("Slardar, Timbersaw e UnderLord")); // Lista de heróis mais jogados
        jogador.setMmr(12000);                      // Define o MMR (ranking de habilidade)
    }

    @Test
    @DisplayName("Should return a player by Id")
    void testBuscarPorId() throws Exception {
        when(jogadorService.buscarPorId(1L)).thenReturn(jogador);

        // Configura o mock para retornar o jogador quando buscarPorId(1L) for chamado
        when(jogadorService.buscarPorId(1L)).thenReturn(jogador);

        // --- Teste da Requisição ---
        mockMvc.perform(get("/jogadores/1")) // Simula uma requisição GET para o endpoint /jogadores/1
                .andExpect(status().isOk()) // Verifica se o status da resposta é 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Verifica se o ID retornado é 1
                .andExpect(jsonPath("$.nome").value("Felipe Astini")) // Verifica se o nome retornado é correto
                .andExpect(jsonPath("$.nickname").value("Ast1ni")) // Verifica se o nickname retornado é correto
                .andExpect(jsonPath("$.posicao").value(3)) // Verifica se a posição retornada é correta
                .andExpect(jsonPath("$.nacionalidade").value("Brasileiro")) // Verifica se a nacionalidade retornada é correta
                .andExpect(jsonPath("$.funcao").value("Offlaner")) // Verifica se a função retornada é correta
                .andExpect(jsonPath("$.mmr").value(12000)) // Verifica se o MMR retornado é correto
                .andExpect(jsonPath("$.heroisMaisJogados").value("Slardar, Timbersaw e UnderLord")); // Verifica se os heróis mais jogados estão corretos
    }

}
