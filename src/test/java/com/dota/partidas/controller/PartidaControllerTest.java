package com.dota.partidas.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.dota.partidas.model.Partida;

import com.dota.partidas.service.PartidaService;

@WebMvcTest(PartidaController.class)
public class PartidaControllerTest {
   
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PartidaService partidaService;

    private Partida partida;
    
    @BeforeEach
    void setup(){
        partida = new Partida();
        partida.setBans(List.of(""));
        partida.setPicks(List.of(""));
        partida.setDiferencaPatrimonioEquipes(5000.00);
        partida.setDuracaoPartida(LocalTime.of(0,45));
        partida.setData(LocalDate.of(2025, 9, 14));
        partida.setPontuacao("2-1");
        
    }
    
    @Test
    @DisplayName("Should get the statistics from the match")
    void testListar() {

    }
}
