package com.rfgvieira.Assistidores.service;

import com.rfgvieira.Assistidores.dto.EpisodioDTO;
import com.rfgvieira.Assistidores.dto.SerieDTO;
import com.rfgvieira.Assistidores.model.Categoria;
import com.rfgvieira.Assistidores.model.Serie;
import com.rfgvieira.Assistidores.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> obterSeries(){
        return converteDados(repository.findAll());
    }

    public List<SerieDTO> obterTop5Series() {
        return converteDados(repository.findTop5ByOrderByAvaliacaoDesc());
    }

    public List<SerieDTO> obterLancamentos() {
        return converteDados(repository.buscarEpisodiosRecentes());
    }

    public SerieDTO obterDetalhesSerie(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(),
                    s.getTitulo(),
                    s.getTotalTemp(),
                    s.getAvaliacao(),
                    s.getAtores(),
                    s.getGenero(),
                    s.getPoster(),
                    s.getSinopse());
        }
        return null;
    }

    public List<EpisodioDTO> obterTemporadasSerie(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream().map(
                    e -> new EpisodioDTO(e.getNumTemporada(), e .getNumEpisodio(), e.getTitulo())).collect(Collectors.toList());
        }
        return null;

    }

    public List<EpisodioDTO> obterTemporadaPorNumero(Long id, Integer num) {
        return repository.obterTemporadaPorNumero(id, num).stream().map(e -> new EpisodioDTO(e.getNumTemporada(), e.getNumEpisodio(), e.getTitulo())).collect(Collectors.toList());
    }

    public List<SerieDTO> obterSeriesPorCategoria(String nome) {
        Categoria categoria= Categoria.fromPort(nome);
        return converteDados(repository.findByGenero(categoria));
    }

    private List<SerieDTO> converteDados(List<Serie> serieList){
        return serieList.stream().map(
                s -> new SerieDTO(
                        s.getId(),
                        s.getTitulo(),
                        s.getTotalTemp(),
                        s.getAvaliacao(),
                        s.getAtores(),
                        s.getGenero(),
                        s.getPoster(),
                        s.getSinopse())).collect(Collectors.toList());
    }
}





