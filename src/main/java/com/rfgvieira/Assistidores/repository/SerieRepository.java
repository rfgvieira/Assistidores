package com.rfgvieira.Assistidores.repository;

import com.rfgvieira.Assistidores.model.Categoria;
import com.rfgvieira.Assistidores.model.Episodio;
import com.rfgvieira.Assistidores.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie,Long> {
    Optional<Serie> findByTituloContainsIgnoreCase(String nome);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);

    @Query( "select s from Serie s where s.totalTemp <= :temporadas and s.avaliacao >= :avaliacao")
    List<Serie> seriesPorTemporadaAvaliacao(int temporadas, Double avaliacao);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nomeTrecho%")
    List<Episodio> episodiosPorTrecho(String nomeTrecho);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> topEpisodiosPorSerie(Serie serie);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie AND YEAR(e.dataLancamento) >= :ano")
    List<Episodio> episodiosSerieAposData(Serie serie, int ano);


    @Query("SELECT s FROM Serie s JOIN s.episodios e WHERE s = e.serie GROUP BY s ORDER BY MAX(e.dataLancamento) DESC LIMIT 5")
    List<Serie> buscarEpisodiosRecentes();

    @Query("Select e FROM Serie s Join s.episodios e WHERE s.id = :id AND s = e.serie AND e.numTemporada = :num ")
    List<Episodio> obterTemporadaPorNumero(Long id, Integer num);
}
