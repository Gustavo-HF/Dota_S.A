package com.dota.partidas.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Jogador {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    private Long id;

    @NotBlank(message="O campo de nome não pode estar vazio")
    private String nome;

    @Column(unique=true)
    @NotBlank(message="O campo de nickname não pode estar vazio")
    private String nickname;

    @NotNull(message="O campo posição não pode estar vazio")
    @Min(value=1, message="As posições devem ir do 1 ao 5")
    @Max(value=5, message="O número da posição não pode ser maior que 5")
    private int posicao;

    @NotBlank(message="Este campo não pode estar vazio")
    private String nacionalidade;

    @NotBlank(message="Este campo não pode estar vazio")
    private String funcao;
    
    @ElementCollection
    @Size(max=3)
    private List<String> heroisMaisJogados;
    
    @Min(value=6000, message="O valor do mmr para um profissional não pode ser menor do que 6000")
    private int mmr;
    

    public Jogador(){

    }

    @ManyToOne
    @JoinColumn(name="time_id")
    private Time time;

    @OneToMany(mappedBy = "jogador")
    private List<JogadorPartida> partidas;
    

    public Jogador(String funcao, List<String> heroisMaisJogados, Long id, int mmr, String nacionalidade, String nickname, String nome, int posicao, Time time, List<JogadorPartida> partidas) {
        this.funcao = funcao;
        this.partidas = partidas;
        this.heroisMaisJogados = heroisMaisJogados;
        this.id = id;
        this.mmr = mmr;
        this.nacionalidade = nacionalidade;
        this.nickname = nickname;
        this.nome = nome;
        this.posicao = posicao;
        this.time = time;
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

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
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

    public int getMmr() {
        return mmr;
    }

    public void setMmr(int mmr) {
        this.mmr = mmr;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public List<JogadorPartida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<JogadorPartida> partidas) {
        this.partidas = partidas;
    }


}

