package com.dota.partidas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dota.partidas.dto.JogadorPartidaDTO;
import com.dota.partidas.exception.JogadorPartidaException.JogadorDevePertencerAPartidaException;
import com.dota.partidas.exception.JogadorPartidaException.JogadorFormatoKDAExcpetion;
import com.dota.partidas.exception.JogadorPartidaException.JogadorJaNaPartidaException;
import com.dota.partidas.exception.JogadorPartidaException.JogadorPartidaNotFoundException;
import com.dota.partidas.exception.JogadorPartidaException.JogadorPartidaRegistroExcpetion;
import com.dota.partidas.model.Jogador;
import com.dota.partidas.model.JogadorPartida;
import com.dota.partidas.model.Partida;
import com.dota.partidas.model.compositkeys.JogadorPartidaId;
import com.dota.partidas.repository.JogadorPartidaRepository;
import com.dota.partidas.repository.JogadorRepository;
import com.dota.partidas.repository.PartidaRepository;

@Service
public class JogadorPartidaService {

    @Autowired
    PartidaRepository partidaRepository;

    @Autowired
    JogadorRepository jogadorRepository;

    @Autowired
    private JogadorPartidaRepository jogadorPartidaRepository;

    /**
     * Vincula um jogador a uma partida específica com suas estatísticas. Este
     * método garante que não haja duplicidade e que o jogador realmente faz
     * parte do confronto.
     */
    public JogadorPartida salvar(JogadorPartidaDTO dto) {

        // 1. HIDRATAÇÃO: Buscamos as entidades completas no banco.
        // O DTO traz apenas IDs, mas precisamos do objeto real para validar regras de negócio complexas.
        Partida partida = partidaRepository.findById(dto.getPartida().getId())
                .orElseThrow(() -> new RuntimeException("Partida não encontrada"));

        Jogador jogador = jogadorRepository.findById(dto.getJogador().getId())
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));

        // 2. VERIFICAÇÃO DE DUPLICIDADE: Evita que o mesmo jogador seja registrado duas vezes na mesma partida.
        boolean jogadorJaNaPartida = jogadorPartidaRepository
                .findByPartidaAndJogador(partida, jogador)
                .isPresent();

        if (jogadorJaNaPartida) {
            throw new JogadorJaNaPartidaException("O jogador já está vinculado a esta partida");
        }

        // 3. CONSISTÊNCIA DE ESCALAÇÃO: Regra crucial. 
        // Verificamos se o jogador pertence à lista de jogadores do Time A ou Time B daquela partida.
        // Isso impede que um jogador "aleatório" seja inserido em uma partida que ele não jogou.
        boolean pertenceAoTimeA = partida.getTimeA().getJogadores().contains(jogador);
        boolean pertenceAoTimeB = partida.getTimeB().getJogadores().contains(jogador);

        if (!pertenceAoTimeA && !pertenceAoTimeB) {
            throw new JogadorDevePertencerAPartidaException("O jogador deve pertencer a um dos times da partida");
        }

        // 4. VALIDAÇÃO DE FORMATO: Garante que o KDA siga o padrão String "K/D/A".
        // O uso de Regex aqui é para manter a padronização visual nas estatísticas.
        if (!dto.getKda().matches("\\d+/\\d+/\\d+")) {
            throw new JogadorFormatoKDAExcpetion("Formato de KDA inválido (ex: 10/2/15)");
        }

        // 5. PERSISTÊNCIA: Com tudo validado, transformamos o DTO em Entidade e salvamos.
        JogadorPartida jogadorPartida = new JogadorPartida();
        jogadorPartida.setJogador(jogador);
        jogadorPartida.setPartida(partida);
        jogadorPartida.setKda(dto.getKda());
        jogadorPartida.setPatrimonioLiquidoIndividual(dto.getPatrimonioLiquidoIndividual());

        return jogadorPartidaRepository.save(jogadorPartida);
    }

    /**
     * Retorna o histórico completo de participações de jogadores em partidas.
     */
    public List<JogadorPartida> listarTodos() {
        return jogadorPartidaRepository.findAll();
    }

    /**
     * Busca um registro específico através da Chave Composta
     * (JogadorPartidaId).
     */
    public JogadorPartida buscarPorId(JogadorPartidaId id) {
        return jogadorPartidaRepository.findById(id)
                .orElseThrow(() -> new JogadorPartidaRegistroExcpetion("Registro de JogadorPartida não encontrado"));
    }

    /**
     * Atualiza dados voláteis (como o KDA) de um registro já existente.
     */
    public JogadorPartida atualizar(JogadorPartidaId id, JogadorPartidaDTO dto) {
        JogadorPartida existente = jogadorPartidaRepository.findById(id)
                .orElseThrow(() -> new JogadorPartidaNotFoundException("Registro não encontrado"));

        // Apenas o KDA é atualizado aqui para preservar a integridade da relação Jogador/Partida
        existente.setKda(dto.getKda());

        return jogadorPartidaRepository.save(existente);
    }

    /**
     * Remove o vínculo de um jogador com uma partida.
     */
    public void excluirPorId(JogadorPartidaId id) {
        jogadorPartidaRepository.deleteById(id);
    }

    /**
     * Limpa todos os registros de estatísticas (Útil para reset de
     * temporada/testes).
     */
    public void excluirTodos() {
        jogadorPartidaRepository.deleteAll();
    }
}
