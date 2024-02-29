package com.rfgvieira.Assistidores.repository;

import com.rfgvieira.Assistidores.model.Categoria;
import com.rfgvieira.Assistidores.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie,Long> {
    Optional<Serie> findByTituloContainsIgnoreCase(String nome);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);

    List<Serie> findByTotalTempLessThanEqualAndAvaliacaoGreaterThanEqual(int tmeporadas, Double avalicao);
}
