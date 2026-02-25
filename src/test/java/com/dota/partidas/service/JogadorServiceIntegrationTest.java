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
    void deveBuscarJogadorPorIdInexistenteERetornarErro(){
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
    @DisplayName("Deve criar e salvar um jogador novo com sucesso")
    void deveCriarESalvarOJogadorComSucesso() {

        //Arrange
        Jogador jogador = new Jogador();
        jogador.setNickname("An4log");
        jogador.setMmr(13000);
        jogador.setFuncao("Mid");
        jogador.setNacionalidade("Brasileiro");
        jogador.setNome("Paulo");
        jogador.setPosicao(2);

        //Act
        jogadorService.salvar(jogador);

        //Assert
        assertEquals("An4log", jogador.getNickname());
        assertEquals(13000, jogador.getMmr());
        assertEquals("Mid", jogador.getFuncao());
        assertEquals("Brasileiro", jogador.getNacionalidade());
        assertEquals("Paulo", jogador.getNome());
        assertEquals(2, jogador.getPosicao());


    }
}
