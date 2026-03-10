package com.dota.partidas.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import com.dota.partidas.dto.JogadorDTO;
import com.dota.partidas.model.Jogador;
import com.dota.partidas.repository.JogadorRepository;

@SpringBootTest
class JogadorServiceTest {

    @InjectMocks
    private JogadorService jogadorService;

    @Mock
    private JogadorRepository jogadorRepository;

    @Test
    @DisplayName("Deve buscar por id e retornar jogador com sucesso")
    void deveBuscarPorIdComSucesso() {

        //Arrange
        Jogador jogador = new Jogador();
        jogador.setId(1L);
        jogador.setNome("Invoker");
        jogador.setNickname("invoker123");
        jogador.setPosicao(2);
        jogador.setNacionalidade("Brasileiro");
        jogador.setFuncao("Mid");
        jogador.setMmr(7000);

        when(jogadorRepository.findById(1L)).thenReturn(Optional.of(jogador));

        //Act
        Jogador resultado = jogadorService.buscarPorId(1L);

        //Assert
        assertNotNull(resultado);
        assertEquals("Invoker", resultado.getNome());
        verify(jogadorRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve buscar por id um jogador inexistente e retornar erro")
    void deveBuscarPorIdSemSucesso() {
        //Arrange
        when(jogadorRepository.findById(99L)).thenReturn(Optional.empty());

        //Act
        Jogador resultado = jogadorService.buscarPorId(99L);

        //Assert
        assertNull(resultado);
        verify(jogadorRepository).findById(99L);
    }

    @Test
    @DisplayName("Deve excluir jogador com sucesso")
    void deveExcluirPorIdComSucesso() {
        //Arrange
        doNothing().when(jogadorRepository).deleteById(1L);

        //Act
        jogadorService.excluirPorId(1L);

        //Assert
        verify(jogadorRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve excluir um jogador inexistente e retornar erro na tentativa")
    void deveExcluirPorIdSemSucesso() {
        //Arrange
        doThrow(new RuntimeException("Jogador não encontrado"))
                .when(jogadorRepository).deleteById(1L);

        //Act + Assert
        assertThrows(RuntimeException.class, () -> jogadorService.excluirPorId(1L));
        verify(jogadorRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve exlcuir todos os jogadores com sucesso")
    void deveExcluirTodosComSucesso() {
        //Arramge
        doNothing().when(jogadorRepository).deleteAll();

        //Act
        jogadorService.excluirTodos();

        //Assert
        verify(jogadorRepository).deleteAll();
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar excluir todos os jogadores")
    void deveExcluirTodosSemSucesso() {

        // Arrange
        doThrow(new RuntimeException("Erro ao excluir jogadores"))
                .when(jogadorRepository).deleteAll();

        // Act + Assert
        assertThrows(RuntimeException.class, () -> jogadorService.excluirTodos());

        // Verify
        verify(jogadorRepository).deleteAll();
    }

    @Test
    @DisplayName("Deve listar todos os jogadores com sucesso")
    void deveListarTodosComSucesso() {
        //Arrange
        Jogador j1 = new Jogador();
        j1.setId(1L);
        j1.setNome("Invoker");

        Jogador j2 = new Jogador();
        j2.setId(2L);
        j2.setNome("Juggernaut");

        List<Jogador> jogadores = Arrays.asList(j1, j2);

        when(jogadorRepository.findAll()).thenReturn(jogadores);

        //Act
        List<Jogador> resultado = jogadorService.listarTodos();

        //Assert
        assertEquals(2, resultado.size());
        verify(jogadorRepository).findAll();
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar listar todos os jogadores")
    void deveListarTodosSemSucesso() {

        // Arrange
        when(jogadorRepository.findAll())
                .thenThrow(new RuntimeException("Erro ao listar jogadores"));

        // Act + Assert
        assertThrows(RuntimeException.class, () -> jogadorService.listarTodos());

        // Verify
        verify(jogadorRepository).findAll();
    }

    @Test
    @DisplayName("Deve criar e salvar um jogador com sucesso")
    void deveSalvarComSucesso() {
        //Arrange
        JogadorDTO dto = new JogadorDTO();
        dto.setNome("Axe");
        dto.setNickname("axe123");
        dto.setPosicao(3);
        dto.setNacionalidade("Brasileiro");
        dto.setFuncao("Offlane");
        dto.setMmr(6500);

        Jogador jogadorSalvo = new Jogador();
        jogadorSalvo.setId(1L);
        jogadorSalvo.setNome("Axe");

        when(jogadorRepository.findByNickname(dto.getNickname()))
                .thenReturn(Optional.empty());

        when(jogadorRepository.save(any(Jogador.class)))
                .thenReturn(jogadorSalvo);

        //Act
        Jogador resultado = jogadorService.salvar(dto);

        //Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Axe", resultado.getNome());

        verify(jogadorRepository).save(any(Jogador.class));
    }

    @Test
    @DisplayName("Deve tentar salvar um jogador inexistente e retornar erro")
    void deveSalvarSemSucesso() {

        //Arrange
        JogadorDTO dto = new JogadorDTO();
        dto.setNome("Axe");

        when(jogadorRepository.save(any(Jogador.class)))
                .thenThrow(new RuntimeException("Erro ao salvar"));

        //Act + Assert
        assertThrows(RuntimeException.class, () -> jogadorService.salvar(dto));
        verify(jogadorRepository).save(any(Jogador.class));
    }
}
