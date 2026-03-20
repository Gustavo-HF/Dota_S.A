package com.dota.partidas.dto;

import java.util.List;

import com.dota.partidas.model.Time;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class JogadorDTO {

    private Long id;

    @NotBlank(message="O campo de nome não pode estar vazio")
    private String nome;

    @NotBlank(message="O campo de nickname não pode estar vazio")
    private String nickname;

    @NotNull(message="O campo posição não pode estar vazio")
    @Min(value=1, message="As posições devem ir do 1 ao 5")
    @Max(value=5, message="O número da posição não pode ser maior que 5")
    private Integer posicao;

    @NotBlank(message="Este campo não pode estar vazio")
    private String nacionalidade;

    @NotBlank(message="Este campo não pode estar vazio")
    private String funcao;

    private List<String> heroisMaisJogados;

    @Min(value=6000, message="O valor do mmr para um profissional não pode ser menor do que 6000")
    private Integer mmr;

    private Time time;
    
    
    public JogadorDTO() {

    }

    public JogadorDTO(Time time, String funcao, List<String> heroisMaisJogados, Long id, Integer mmr, String nacionalidade, String nickname, String nome, Integer posicao) {
        this.time = time;
        this.funcao = funcao;
        this.heroisMaisJogados = heroisMaisJogados;
        this.id = id;
        this.mmr = mmr;
        this.nacionalidade = nacionalidade;
        this.nickname = nickname;
        this.nome = nome;
        this.posicao = posicao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public List<String> getHeroisMaisJogados() {
        return heroisMaisJogados;
    }

    public void setHeroisMaisJogados(List<String> heroisMaisJogados) {
        this.heroisMaisJogados = heroisMaisJogados;
    }

    public Integer getMmr() {
        return mmr;
    }

    public void setMmr(Integer mmr) {
        this.mmr = mmr;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }


    
}

