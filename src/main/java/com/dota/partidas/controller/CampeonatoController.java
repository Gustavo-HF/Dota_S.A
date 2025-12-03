package com.dota.partidas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dota.partidas.model.Campeonato;
import com.dota.partidas.service.CampeonatoService;


@RestController
@RequestMapping("/campeonato")
public class CampeonatoController {
    @Autowired
    private CampeonatoService campeonatoService;
    
    // Listar todos
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("campeonatos", campeonatoService.listarCampeonato());
        return "campeonatos"; // nome da view HTML
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public String buscarPorId(@PathVariable Long id, Model model) {
        model.addAttribute("campeonato", campeonatoService.buscarPorId(id));
        return "detalheCampeonato";
    }

    // Salvar
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Campeonato campeonato) {
        campeonatoService.salvarCampeonato(campeonato);
        return "redirect:/campeonatos";
    }

   @PutMapping("/{id}")
    public Campeonato atualizar(@PathVariable Long id, @RequestBody Campeonato campeonato) {
        Campeonato existente = campeonatoService.buscarPorId(id);
        existente.setNome(campeonato.getNome());
        existente.setComecoCamp(campeonato.getComecoCamp());
        existente.setFimCamp(campeonato.getFimCamp());
        existente.setPatchCampeonato(campeonato.getPatchCampeonato());
        existente.setPartidas(campeonato.getPartidas());
        return campeonatoService.salvarCampeonato(existente);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        // aqui você pode criar um método específico no service para deletar por ID
        campeonatoService.buscarPorId(id); 
        campeonatoService.deletarCampeonato(); 
    }

    

}
