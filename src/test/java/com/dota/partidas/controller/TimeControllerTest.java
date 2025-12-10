package com.dota.partidas.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dota.partidas.model.Time;
import com.dota.partidas.service.TimeService;

@SpringBootTest
@AutoConfigureMockMvc
public class TimeControllerTest {

     @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TimeService timeService;
    
    @Test
    void testExcluir() throws Exception {
        Long idTime = 1L;

            // Simula que o time existe antes do delete
            Time timeExistente = new Time();
            timeExistente.setId(idTime);
            timeExistente.setNome("Team Radiant");

            Mockito.when(timeService.buscarPorId(idTime)).thenReturn(timeExistente);

            // Executa o DELETE pelo MockMvc
            mockMvc.perform(MockMvcRequestBuilders
                    .delete("/times/{id}", idTime))
                    .andExpect(status().isNoContent()); // 204

            // Verifica se o service.delete() foi chamado corretamente
            Mockito.verify(timeService, Mockito.times(1)).excluirPorId(idTime);
    }
}
