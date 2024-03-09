package com.rfgvieira.Assistidores.dto;

import com.rfgvieira.Assistidores.model.Categoria;

public record SerieDTO(Long id,
                       String titulo,
                       Integer totalTemp,
                       Double avaliacao,
                       String atores,
                       Categoria genero,
                       String poster,
                       String sinopse) {
}
