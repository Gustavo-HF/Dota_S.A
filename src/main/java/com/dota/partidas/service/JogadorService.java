package com.dota.partidas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dota.partidas.dto.JogadorDTO;
import com.dota.partidas.exception.JogadorException.JogadorMMRMinimoException;
import com.dota.partidas.exception.JogadorException.JogadorMaximoHeroisMaisJogadosException;
import com.dota.partidas.exception.JogadorException.JogadorNicknameJaExistenteException;
import com.dota.partidas.exception.JogadorException.JogadorNotFoundException;
import com.dota.partidas.model.Jogador;
import com.dota.partidas.repository.JogadorRepository;

@Service
public class JogadorService {

    @Autowired
    private JogadorRepository jogadorRepository;

    /**
     * Cria um novo jogador aplicando filtros de qualidade e unicidade. O
     * objetivo é garantir que o banco reflita apenas jogadores de alto nível.
     */
    public Jogador salvar(JogadorDTO jogadorDTO) {

        // 1. FILTRO DE ELITE (MMR): 
        // Definimos uma barreira de entrada. Se o jogador não tiver o MMR mínimo (6000),
        // nem prosseguimos com a persistência. Isso mantém o nível profissional do sistema.
        if (jogadorDTO.getMmr() < 6000) {
            throw new JogadorMMRMinimoException("O MMR mínimo para jogador profissional é 6000");
        }

        // 2. RECONHECIMENTO ÚNICO (Nickname): 
        // O nickname é a identidade do atleta. Validamos no banco se já existe para evitar duplicidade,
        // garantindo que cada "tag" seja exclusiva no ecossistema.
        if (jogadorRepository.findByNickname(jogadorDTO.getNickname()).isPresent()) {
            throw new JogadorNicknameJaExistenteException("Já existe um jogador com esse nickname");
        }

        // 3. LIMITAÇÃO DE ESCOPO (Heróis): 
        // Para fins de análise e estatística, focamos no "Top 3" do jogador. 
        // Validamos o tamanho da lista para evitar poluição de dados no perfil do atleta.
        if (jogadorDTO.getHeroisMaisJogados() != null && jogadorDTO.getHeroisMaisJogados().size() > 3) {
            throw new JogadorMaximoHeroisMaisJogadosException("Um jogador pode ter no máximo 3 heróis mais jogados");
        }

        // Mapeamento de DTO para Entidade
        Jogador jogador = new Jogador();
        jogador.setId(jogadorDTO.getId());
        jogador.setNome(jogadorDTO.getNome());
        jogador.setFuncao(jogadorDTO.getFuncao());
        jogador.setHeroisMaisJogados(jogadorDTO.getHeroisMaisJogados());
        jogador.setNacionalidade(jogadorDTO.getNacionalidade());
        jogador.setMmr(jogadorDTO.getMmr());
        jogador.setPosicao(jogadorDTO.getPosicao());
        jogador.setNickname(jogadorDTO.getNickname());

        return jogadorRepository.save(jogador);
    }

    /**
     * Retorna a lista de todos os atletas. Lancei uma exceção caso a lista
     * esteja vazia para evitar que o Front-end lide com estados nulos
     * inesperados.
     */
    public List<Jogador> listarTodos() {
        List<Jogador> jogadores = jogadorRepository.findAll();
        if (jogadores.isEmpty()) {
            throw new JogadorNotFoundException("Nenhum jogador encontrado");
        }
        return jogadores;
    }

    /**
     * Busca um atleta pelo ID. Essencial para visualização de perfis
     * individuais.
     */
    public Jogador buscarPorId(Long id) {
        return jogadorRepository.findById(id)
                .orElseThrow(() -> new JogadorNotFoundException("Jogador não encontrado"));
    }

    /**
     * Atualiza os dados do atleta. Note que aqui permitimos a transferência de
     * time (setTime) caso o DTO informe um novo vínculo.
     */
    public Jogador atualizar(Long id, JogadorDTO dto) {
        Jogador existente = jogadorRepository.findById(id)
                .orElseThrow(() -> new JogadorNotFoundException("Jogador não encontrado"));

        // Atualização de atributos básicos e estatísticos
        existente.setNome(dto.getNome());
        existente.setNickname(dto.getNickname());
        existente.setNacionalidade(dto.getNacionalidade());
        existente.setPosicao(dto.getPosicao());
        existente.setFuncao(dto.getFuncao());
        existente.setMmr(dto.getMmr());
        existente.setHeroisMaisJogados(dto.getHeroisMaisJogados());

        // Lógica de Transferência: Se o DTO trouxer um time, atualizamos o vínculo do jogador
        if (dto.getTime() != null) {
            existente.setTime(dto.getTime());
        }

        return jogadorRepository.save(existente);
    }

    /**
     * Remove um jogador do sistema por ID. Antes de deletar, validamos a
     * existência para garantir que a operação é válida.
     */
    public void excluirPorId(Long id) {
        Jogador jogador = jogadorRepository.findById(id)
                .orElseThrow(() -> new JogadorNotFoundException("Jogador não encontrado"));

        jogadorRepository.delete(jogador);
    }

    /**
     * Limpa toda a base de jogadores.
     */
    public void excluirTodos() {
        jogadorRepository.deleteAll();
    }
}
