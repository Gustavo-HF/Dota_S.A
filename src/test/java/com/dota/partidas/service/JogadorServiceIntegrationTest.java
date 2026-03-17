package com.dota.partidas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dota.partidas.dto.JogadorDTO;
import com.dota.partidas.exception.JogadorException.JogadorNotFoundException;
import com.dota.partidas.model.Jogador;
import com.dota.partidas.repository.JogadorRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class JogadorServiceIntegrationTest {

    @Autowired
    private JogadorRepository jogadorRepository;

    @Autowired
    private JogadorService jogadorService;

    @Test
    @DisplayName("Deve buscar um jogador pelo id e retornar com sucesso")
    void deveBuscarPorIdERetornarComSucesso() {
        //Arrange
        Jogador jogador = new Jogador();
        jogador.setNickname("miracle");
        jogador.setMmr(8000);
        jogadorRepository.save(jogador);

        //Act
        Jogador encontrado = jogadorService.buscarPorId(jogador.getId());

        //Assert
        assertNotNull(encontrado);
        assertEquals("miracle", encontrado.getNickname());
        assertEquals(8000, encontrado.getMmr());

    }

    @Test
    @DisplayName("Deve retornar exeção quando procurar jogador com id inexistente")
    void deveBuscarJogadorPorIdInexistenteERetornarErro() {
        //Act + Assert
        assertThrows(JogadorNotFoundException.class, () -> {
            jogadorService.buscarPorId(1L);
        });
    }

    @Test
    @DisplayName("Deve excluir um jogador pelo id")
    void deveExcluirJogadorPorIdComSucesso() {

        //Arrange
        Jogador jogador = new Jogador();
        jogador.setNickname("An4log");
        jogador.setMmr(13000);
        jogador.setFuncao("Mid");
        jogador.setNacionalidade("Brasileiro");
        jogador.setNome("Paulo");
        jogador.setPosicao(2);

        jogadorRepository.save(jogador);

        Long id = jogador.getId();

        //Validar existência
        assertTrue(jogadorRepository.existsById(id));

        //Act
        jogadorService.excluirPorId(id);

        //Assert
        assertFalse(jogadorRepository.existsById(id));

    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar jogador inexistente")
    void deveBuscarJogadorInexistenteERetornarErro() {

        // Act + Assert
        assertThrows(JogadorNotFoundException.class, () -> {
            jogadorService.buscarPorId(1L);
        });

    }

    @Test
    @DisplayName("Deve exlcuir todos os jogadores com sucesso")
    void deveExcluirTodosOsJogadoresComSucesso() {

        //Arrange
        Jogador jogador = new Jogador();
        jogador.setNickname("An4log");
        jogador.setMmr(13000);
        jogador.setFuncao("Mid");
        jogador.setNacionalidade("Brasileiro");
        jogador.setNome("Paulo");
        jogador.setPosicao(2);

        Jogador jogador2 = new Jogador();
        jogador2.setNickname("Yatoro");
        jogador2.setMmr(17500);
        jogador2.setFuncao("HC");
        jogador2.setNacionalidade("Brasileiro");
        jogador2.setNome("Jonas");
        jogador2.setPosicao(1);

        jogadorRepository.save(jogador);
        jogadorRepository.save(jogador2);

        //Act
        jogadorService.excluirTodos();

        //Assert
        assertTrue(jogadorRepository.findAll().isEmpty());

    }

    @Test
    @DisplayName("Deve lançar exceção ao excluir jogador inexistente")
    void deveExcluirJogadorInexistente() {

        // Act + Assert
        assertThrows(JogadorNotFoundException.class, () -> {
            jogadorService.excluirPorId(1L);
        });

    }

    @Test
    @DisplayName("Deve listar todos os jogadores da partida com sucesso")
    void deveListarTodosOsJogadoresComSucesso() {

        //Arrange
        Jogador jogador = new Jogador();
        jogador.setNickname("An4log");
        jogador.setMmr(13000);
        jogador.setFuncao("Mid");
        jogador.setNacionalidade("Brasileiro");
        jogador.setNome("Paulo");
        jogador.setPosicao(2);

        Jogador jogador2 = new Jogador();
        jogador2.setNickname("Yatoro");
        jogador2.setMmr(17500);
        jogador2.setFuncao("HC");
        jogador2.setNacionalidade("Brasileiro");
        jogador2.setNome("Jonas");
        jogador2.setPosicao(1);

        jogadorRepository.save(jogador);
        jogadorRepository.save(jogador2);

        //Act
        jogadorService.listarTodos();

        //Assert
        assertTrue(jogadorRepository.findAll().contains(jogador));
        assertTrue(jogadorRepository.findAll().contains(jogador2));

    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver jogadores cadastrados")
    void deveListarTodosOsJogadoresSemSucesso() {

        // Arrange
        jogadorRepository.deleteAll();

        // Act
        var jogadores = jogadorService.listarTodos();

        // Assert
        assertTrue(jogadores.isEmpty());
    }

    @Test
    @DisplayName("Deve criar e salvar um jogador novo com sucesso")
    void deveCriarESalvarOJogadorComSucesso() {

        //Arrange
        JogadorDTO jogadorDTO = new JogadorDTO();
        jogadorDTO.setNickname("An4log");
        jogadorDTO.setMmr(13000);
        jogadorDTO.setFuncao("Mid");
        jogadorDTO.setNacionalidade("Brasileiro");
        jogadorDTO.setNome("Paulo");
        jogadorDTO.setPosicao(2);

        //Act
        jogadorService.salvar(jogadorDTO);

        //Assert
        assertEquals("An4log", jogadorDTO.getNickname());
        assertEquals(13000, jogadorDTO.getMmr());
        assertEquals("Mid", jogadorDTO.getFuncao());
        assertEquals("Brasileiro", jogadorDTO.getNacionalidade());
        assertEquals("Paulo", jogadorDTO.getNome());
        assertEquals(2, jogadorDTO.getPosicao());

    }

    @Test
    @DisplayName("Não deve salvar jogador com MMR abaixo do mínimo")
    void naoDeveSalvarJogadorComMMRInvalido() {

        // Arrange
        JogadorDTO jogadorDTO = new JogadorDTO();
        jogadorDTO.setNickname("player");
        jogadorDTO.setMmr(3000);

        // Act + Assert
        assertThrows(RuntimeException.class, () -> {
            jogadorService.salvar(jogadorDTO);
        });

    }

    @Test
    @DisplayName("Não deve salvar jogador com nickname duplicado")
    void naoDeveSalvarJogadorComNicknameDuplicado() {

        // Arrange
        Jogador jogador = new Jogador();
        jogador.setNickname("Miracle");
        jogador.setMmr(8000);

        jogadorRepository.save(jogador);

        JogadorDTO jogadorDTO = new JogadorDTO();
        jogadorDTO.setNickname("Miracle");
        jogadorDTO.setMmr(9000);

        // Act + Assert
        assertThrows(RuntimeException.class, () -> {
            jogadorService.salvar(jogadorDTO);
        });

    }

    @Test
    @DisplayName("Não deve salvar jogador com mais de 3 heróis mais jogados")
    void naoDeveSalvarJogadorComMaisDeTresHerois() {

        // Arrange
        JogadorDTO jogadorDTO = new JogadorDTO();
        jogadorDTO.setNickname("Player");
        jogadorDTO.setMmr(8000);

        jogadorDTO.setHeroisMaisJogados(
                java.util.List.of("Invoker", "Pudge", "Axe", "Juggernaut")
        );

        // Act + Assert
        assertThrows(RuntimeException.class, () -> {
            jogadorService.salvar(jogadorDTO);
        });

    }

    @Test
    @DisplayName("Deve atualizar jogador com sucesso")
    void deveAtualizarJogadorComSucesso() {

        // Arrange (cria e salva um jogador)
        Jogador jogador = new Jogador();
        jogador.setNickname("miracle");
        jogador.setMmr(8000);
        jogador.setFuncao("Mid");
        jogador.setNacionalidade("Brasileiro");
        jogador.setNome("Paulo");
        jogador.setPosicao(2);

        jogador = jogadorRepository.save(jogador);

        // DTO com novos dados
        JogadorDTO dto = new JogadorDTO();
        dto.setNickname("miracleNew"); // novo nick para evitar duplicidade
        dto.setMmr(9000);
        dto.setFuncao("Carry");
        dto.setNacionalidade("Europeu");
        dto.setNome("Miracle-");
        dto.setPosicao(1);

        // Act
        Jogador atualizado = jogadorService.atualizar(jogador.getId(), dto);

        // Assert
        assertNotNull(atualizado);
        assertEquals("Paulo", atualizado.getNome());
        assertEquals(9000, atualizado.getMmr());
        assertEquals("Mid", atualizado.getFuncao());

        // Confirma no banco
        Jogador doBanco = jogadorRepository.findById(jogador.getId()).orElseThrow();
        assertEquals("Paulo", doBanco.getNome());
        assertEquals(9000, doBanco.getMmr());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar jogador inexistente")
    void deveFalharAoAtualizarJogadorInexistente() {

        // Arrange
        JogadorDTO dto = new JogadorDTO();
        dto.setNickname("teste");
        dto.setMmr(8000);

        // Act + Assert
        assertThrows(JogadorNotFoundException.class, () -> {
            jogadorService.atualizar(999L, dto);
        });
    }
}
