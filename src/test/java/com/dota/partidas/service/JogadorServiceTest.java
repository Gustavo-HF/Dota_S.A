package com.dota.partidas.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dota.partidas.dto.JogadorDTO;
import com.dota.partidas.exception.JogadorException.JogadorMMRMinimoException;
import com.dota.partidas.exception.JogadorException.JogadorMaximoHeroisMaisJogadosException;
import com.dota.partidas.exception.JogadorException.JogadorNicknameJaExistenteException;
import com.dota.partidas.exception.JogadorException.JogadorNotFoundException;
import com.dota.partidas.model.Jogador;
import com.dota.partidas.repository.JogadorRepository;

@ExtendWith(MockitoExtension.class)
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
        // Arrange
        when(jogadorRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(JogadorNotFoundException.class, () -> {
            jogadorService.buscarPorId(99L);
        });

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
    @DisplayName("Deve lançar erro ao salvar jogador com MMR abaixo do mínimo")
    void deveSalvarComMmrInvalido() {

        // Arrange
        JogadorDTO dto = new JogadorDTO();
        dto.setMmr(5000); // inválido

        // Act + Assert
        assertThrows(JogadorMMRMinimoException.class, () -> {
            jogadorService.salvar(dto);
        });

        verify(jogadorRepository, never()).save(any()); // 🔥 importante
    }

    @Test
    @DisplayName("Deve lançar erro ao salvar jogador com nickname duplicado")
    void deveSalvarComNicknameDuplicado() {

        // Arrange
        JogadorDTO dto = new JogadorDTO();
        dto.setMmr(7000);
        dto.setNickname("nickExistente");

        when(jogadorRepository.findByNickname("nickExistente"))
                .thenReturn(Optional.of(new Jogador()));

        // Act + Assert
        assertThrows(JogadorNicknameJaExistenteException.class, () -> {
            jogadorService.salvar(dto);
        });

        verify(jogadorRepository).findByNickname("nickExistente");
        verify(jogadorRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro ao salvar jogador com mais de 3 heróis")
    void deveSalvarComMuitosHerois() {

        // Arrange
        JogadorDTO dto = new JogadorDTO();
        dto.setMmr(7000);
        dto.setNickname("nickNovo");
        dto.setHeroisMaisJogados(Arrays.asList("Invoker", "Pudge", "SF", "PA")); // 4 heróis

        when(jogadorRepository.findByNickname("nickNovo"))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(JogadorMaximoHeroisMaisJogadosException.class, () -> {
            jogadorService.salvar(dto);
        });

        verify(jogadorRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando falha ao salvar no banco")
    void deveFalharNoSave() {

        // Arrange
        JogadorDTO dto = new JogadorDTO();
        dto.setMmr(7000);
        dto.setNickname("nickNovo");

        when(jogadorRepository.findByNickname("nickNovo"))
                .thenReturn(Optional.empty());

        when(jogadorRepository.save(any(Jogador.class)))
                .thenThrow(new RuntimeException("Erro no banco"));

        // Act + Assert
        assertThrows(RuntimeException.class, () -> {
            jogadorService.salvar(dto);
        });

        verify(jogadorRepository).save(any(Jogador.class)); // ✅ agora faz sentido
    }

    @Test
    @DisplayName("Deve atualizar jogador com sucesso")
    void deveAtualizarComSucesso() {

        // Arrange
        Long id = 1L;

        Jogador existente = new Jogador();
        existente.setId(id);
        existente.setNome("Invoker");
        existente.setNickname("oldNick");
        existente.setMmr(6500);

        JogadorDTO dto = new JogadorDTO();
        dto.setNome("Invoker Atualizado");
        dto.setNickname("newNick");
        dto.setMmr(8000);

        when(jogadorRepository.findById(id))
                .thenReturn(Optional.of(existente));

        when(jogadorRepository.save(any(Jogador.class)))
                .thenReturn(existente);

        // Act
        Jogador resultado = jogadorService.atualizar(id, dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Invoker", resultado.getNome());
        assertEquals("newNick", resultado.getNickname());
        assertEquals(8000, resultado.getMmr());

        verify(jogadorRepository).findById(id);
        verify(jogadorRepository).save(existente);
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar atualizar jogador inexistente")
    void deveAtualizarSemSucesso() {

        // Arrange
        Long id = 99L;

        JogadorDTO dto = new JogadorDTO();
        dto.setNome("Teste");

        when(jogadorRepository.findById(id))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(RuntimeException.class, () -> {
            jogadorService.atualizar(id, dto);
        });

        verify(jogadorRepository).findById(id);
        verify(jogadorRepository, never()).save(any());
    }
}
