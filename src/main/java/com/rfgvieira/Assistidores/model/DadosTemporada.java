package com.rfgvieira.Assistidores.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(@JsonAlias("Season") Integer numTemporada,
                             @JsonAlias("Episodes") List<DadosEpisodio> episodioList) {
}
