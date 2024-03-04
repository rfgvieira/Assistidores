package com.rfgvieira.Assistidores.principal;

import com.rfgvieira.Assistidores.model.*;
import com.rfgvieira.Assistidores.repository.SerieRepository;
import com.rfgvieira.Assistidores.service.ConsumoApi;
import com.rfgvieira.Assistidores.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private final Scanner sc = new Scanner(System.in);
    private final String CORE_URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=5ec35de3";
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();
    private final SerieRepository serieRepositorio;

    private Optional<Serie> serieBuscada;


    public Principal(SerieRepository serieRepositorio) {
        this.serieRepositorio = serieRepositorio;
    }


    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar Série
                    2 - Buscar Episódios
                    3 - Listar Séries
                    4 - Buscar Série Por Nome
                    5 - Buscar Série Por Ator
                    6 - Buscar Top 5 Séries
                    7 - Buscar Série Por Categoria
                    8 - Buscar Série Por Temporada
                    9 - Buscar Episódio por Trecho
                    10 - Top 5 Episodios Da Serie
                    11 - Buscar Episodios Depois de Data
                    0 - Sair
                    """;

            System.out.println(menu);

            System.out.print("Digite a opção desejada: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerie();
                    break;
                case 2:
                    buscarEpisodiosSerie();
                    break;
                case 3:
                    listarSeries();
                    break;
                case 4:
                    buscarSerieTitulo();
                    break;
                case 5:
                    buscarSerieAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriesPorCategoria();
                    break;
                case 8:
                    buscarSeriesPorTemporadas();
                    break;
                case 9:
                    buscarEpisodioPorTrecho();
                    break;
                case 10:
                    buscarTop5EpisodiosPorSeries();
                    break;
                case 11:
                    buscarEpisodiosDepoisDeData();
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção invalida!");
            }
        }


    }




    private void buscarSerie() {
        DadosSerie serieDados = getSerie();
        Serie serie = new Serie(serieDados);
        serieRepositorio.save(serie);

        System.out.println(serieDados);
    }

    private DadosSerie getSerie() {
        System.out.print("Digite a serie desejada: ");
        var serieEscolhida = sc.nextLine();

        var url = CORE_URL + serieEscolhida.replace(" ", "+") + API_KEY;
        var json = consumoApi.obterDados(url);

        return conversor.obterDados(json, DadosSerie.class);
    }

    private void buscarEpisodiosSerie() {
        listarSeries();
        System.out.print("\nDigite a série que deseja procurar: ");
        var nomeSerie = sc.nextLine();

        Optional<Serie> serie = serieRepositorio.findByTituloContainsIgnoreCase(nomeSerie);

        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadaList = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemp(); i++) {
                var json = consumoApi.obterDados(CORE_URL + serieEncontrada.getTitulo().replace(" ", "+") + "&Season=" + i + "&apikey=5ec35de3");
                DadosTemporada temporadaDados = conversor.obterDados(json, DadosTemporada.class);
                temporadaList.add(temporadaDados);
            }

            temporadaList.forEach(System.out::println);

            List<Episodio> episodios = temporadaList.stream()
                    .flatMap(t -> t.episodioList().stream()
                            .map(e -> new Episodio(t.numTemporada(), e))
                    ).collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            serieRepositorio.save(serieEncontrada);
        } else {
            System.out.println("Série não encontrada!");
        }


    }

    private void listarSeries() {
        List<Serie> series = serieRepositorio.findAll();
        series.stream().sorted(Comparator.comparing(Serie::getGenero)).forEach(System.out::println);

    }

    private void buscarSerieTitulo() {
        System.out.print("\nDigite a série que deseja procurar: ");
        var nomeSerie = sc.nextLine();
        serieBuscada = serieRepositorio.findByTituloContainsIgnoreCase(nomeSerie);
        if (serieBuscada.isPresent()) {
            System.out.println("Dados da Série: " + serieBuscada.get());
        } else {
            System.out.println("Série não Encontrada!");
        }

    }

    private void buscarSerieAtor() {
        System.out.print("Digite o nome desejado: ");
        var nomeAtor = sc.nextLine();
        System.out.println("Digite a nota minima de series que ele tenha participado: ");
        var nota = sc.nextLine();
        List<Serie> seriesEncontradas = serieRepositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, Double.valueOf(nota));
        System.out.println("Séries que" + nomeAtor + " trabalha");
        seriesEncontradas.forEach(s -> System.out.println(s.getTitulo() + " avaliacao: " + s.getAvaliacao()));
    }

    private void buscarTop5Series() {
        List<Serie> topSeries = serieRepositorio.findTop5ByOrderByAvaliacaoDesc();
        topSeries.forEach(s -> System.out.println(s.getTitulo() + " avaliacao: " + s.getAvaliacao()));
    }

    private void buscarSeriesPorCategoria() {
        System.out.print("Digite a Categoria Procurada!: ");
        var categoriaBuscada = sc.nextLine();
        Categoria categoria = Categoria.fromPort(categoriaBuscada);
        List<Serie> seriesPorCategoria = serieRepositorio.findByGenero(categoria);
        System.out.println("\nSéries da categoria " + categoriaBuscada);
        seriesPorCategoria.forEach(System.out::println);
    }

    private void buscarSeriesPorTemporadas() {
        System.out.print("Digite o número de temporadas máximo: ");
        var tempMax = sc.nextLine();
        System.out.print("Digite a avalição mínima: ");
        var nota = sc.nextLine();
        List<Serie> seriesEncontradas = serieRepositorio.seriesPorTemporadaAvaliacao(Integer.parseInt(tempMax), Double.valueOf(nota));
        System.out.println("\nSéries Encontradas:");
        seriesEncontradas.forEach(System.out::println);
    }

    private void buscarEpisodioPorTrecho() {
        System.out.print("Digite o trecho do nome de um episodio: ");
        var nomeTrecho = sc.nextLine();
        List<Episodio> episodiosFiltrados = serieRepositorio.episodiosPorTrecho(nomeTrecho);
        episodiosFiltrados.forEach(e ->
                System.out.printf("Série: %s Temporada: %s - Episódio: %s - %s\n",
                        e.getSerie().getTitulo(), e.getNumTemporada(), e.getNumEpisodio(), e.getTitulo()));
    }

    private void buscarTop5EpisodiosPorSeries() {
        buscarSerieTitulo();
        if (serieBuscada.isPresent()) {
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = serieRepositorio.topEpisodiosPorSerie(serie);
            topEpisodios.forEach(e ->
                    System.out.printf("Série: %s Temporada: %s - Episódio: %s - %s - Avaliacao: %f\n ",
                            e.getSerie().getTitulo(), e.getNumTemporada(), e.getNumEpisodio(), e.getTitulo(), e.getAvaliacao()));
        }
    }

    private void buscarEpisodiosDepoisDeData() {
        buscarSerieTitulo();
        if (serieBuscada.isPresent()) {
            Serie serie = serieBuscada.get();
            System.out.print("Digite o ano desejado: ");
            var ano = sc.nextLine();
            List<Episodio> seriesEncontradas = serieRepositorio.episodiosSerieAposData(serie, Integer.parseInt(ano));
            seriesEncontradas.forEach(System.out::println);
        }

    }

}
