package com.rfgvieira.Assistidores.model;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Episodio {
    private Integer numTemporada;
    private String titulo;
    private Integer numEpisodio;
    private Double avaliacao;
    private LocalDate dataLancamento;

    public Episodio(Integer numeroTemporada, DadosEpisodio dadosEpisodio){
        this.numTemporada = numeroTemporada;
        this.titulo = dadosEpisodio.titulo();
        this.numEpisodio = dadosEpisodio.numEpisodio();

        try {
            this.avaliacao = Double.valueOf(dadosEpisodio.avaliacao());
        } catch (NumberFormatException e) {
            this.avaliacao = 0.0;
        }

        try{
            this.dataLancamento = LocalDate.parse(dadosEpisodio.data());
        } catch (DateTimeException e){
            this.dataLancamento = null;
        }

    }



    public Integer getNumTemporada() {
        return numTemporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getNumEpisodio() {
        return numEpisodio;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setNumTemporada(Integer numTemporada) {
        this.numTemporada = numTemporada;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setNumEpisodio(Integer numEpisodio) {
        this.numEpisodio = numEpisodio;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return
                "titulo='" + titulo +
                ", numTemporada=" + numTemporada +
                ", numEpisodio=" + numEpisodio +
                ", avaliacao=" + avaliacao +
                ", dataLancamento=" + dataLancamento
                ;
    }
}
