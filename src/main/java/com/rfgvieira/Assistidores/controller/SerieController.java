package com.rfgvieira.Assistidores.controller;

import com.rfgvieira.Assistidores.dto.EpisodioDTO;
import com.rfgvieira.Assistidores.dto.SerieDTO;
import com.rfgvieira.Assistidores.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService service;

    @GetMapping
    public List<SerieDTO> obterSeries() {
        return service.obterSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Series(){
        return service.obterTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos(){
        return service.obterLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obterDetalhesSerie(@PathVariable Long id){
        return service.obterDetalhesSerie(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTemporadasSerie(@PathVariable Long id){
        return service.obterTemporadasSerie(id);
    }

    @GetMapping("/{id}/temporadas/{num}")
    public List<EpisodioDTO> obterTemporadaPorNumero(@PathVariable Long id, @PathVariable Integer num){
        return service.obterTemporadaPorNumero(id, num);
    }

    @GetMapping("/categoria/{nome}")
    public List<SerieDTO> obterSeriesPorCategoria(@PathVariable String nome){
        return service.obterSeriesPorCategoria(nome);

    }


}
