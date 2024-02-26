package com.rfgvieira.Assistidores.principal;

import com.rfgvieira.Assistidores.model.DadosEpisodio;
import com.rfgvieira.Assistidores.model.DadosSerie;
import com.rfgvieira.Assistidores.model.DadosTemporada;
import com.rfgvieira.Assistidores.model.Episodio;
import com.rfgvieira.Assistidores.service.ConsumoApi;
import com.rfgvieira.Assistidores.service.ConverteDados;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private final Scanner sc = new Scanner(System.in);
    private final String CORE_URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=5ec35de3";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    public void exibirMenu() {
        System.out.print("Digite a serie desejada: ");
        var serieEscolhida = sc.nextLine();

        var url = CORE_URL + serieEscolhida.replace(" ", "+") + API_KEY;
        var json = consumoApi.obterDados(url);

        DadosSerie serieDados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(serieDados);

        List<DadosTemporada> temporadaList = new ArrayList<>();

        for (int i = 1; i <= serieDados.totalTemp(); i++) {
            json = consumoApi.obterDados(CORE_URL + serieEscolhida.replace(" ", "+") + "&Season=" + i + "&apikey=5ec35de3");
            DadosTemporada temporadaDados = conversor.obterDados(json, DadosTemporada.class);
            temporadaList.add(temporadaDados);
        }

        List<DadosEpisodio> dadosEpisodios = temporadaList.stream().flatMap(
                t -> t.episodioList().stream()
        ).collect(Collectors.toList());

        List<Episodio> episodios = temporadaList.stream().flatMap(
                t -> t.episodioList().stream().map(e -> new Episodio(t.numTemporada(), e))).collect(Collectors.toList());

        //episodios.forEach(System.out::println);


        Map<Integer, Double> avalTemporada = episodios.stream().filter(e -> e.getAvaliacao() > 0.0).collect(
                Collectors.groupingBy(
                        Episodio::getNumTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(avalTemporada);

        DoubleSummaryStatistics est  = episodios.stream().filter(e -> e.getAvaliacao() > 0.0).collect(
                Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor Episódio: " + est.getMax());
        System.out.println("Pior Episódio: " + est.getMin());


//        temporadaList.forEach(System.out::println);
//
//        temporadaList.forEach(
//                t -> t.episodioList().forEach(
//                        e -> System.out.println(e.titulo()))
//        );


//        System.out.println("Top 5 Episodios");
//        dadosEpisodios.stream().filter(e -> !e.avaliacao().equalsIgnoreCase("N/A")).sorted(
//                Comparator.comparing(DadosEpisodio::avaliacao).reversed()
//        ).limit(5).forEach(System.out::println);


//        System.out.print("Digite o nome do episódio desejado: ");
//        var episodeName = sc.nextLine();
//
//        var episodioEncontrado = episodios.stream().filter(e -> e.getTitulo().toLowerCase().contains(episodeName.toLowerCase())).findFirst();
//
//        if(episodioEncontrado.isPresent())
//            System.out.println("Temporada " + episodioEncontrado.get().getNumTemporada());
//        else
//            System.out.println("Nenhum episódio encontrado");

//        System.out.print("A partir de que ano deseja visualizar os eps: ");
//        var ano = sc.nextInt();
//        sc.nextLine();
//
//        LocalDate dataProcurada = LocalDate.of(ano,1,1);
//
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream().filter(
//                e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataProcurada)
//        ).forEach(e -> {
//            System.out.println(
//                    "Temporada: " + e.getNumTemporada() +
//                    " Episódio:" + e.getNumEpisodio() +
//                    " Data Lançamento:" + e.getDataLancamento().format(df));
//        });


    }
}
