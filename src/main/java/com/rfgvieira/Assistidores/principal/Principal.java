package com.rfgvieira.Assistidores.principal;

import com.rfgvieira.Assistidores.model.DadosSerie;
import com.rfgvieira.Assistidores.model.DadosTemporada;
import com.rfgvieira.Assistidores.service.ConsumoApi;
import com.rfgvieira.Assistidores.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private final Scanner sc = new Scanner(System.in);
    private final String CORE_URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=5ec35de3";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    public void exibirMenu() {
        var menu = """
                1 - Buscar série
                2 - Buscar episódios
                0 - Sair
                """;

        System.out.println(menu);

        var opcao = sc.nextInt();
        sc.nextLine();

        switch (opcao){
            case 1: buscarSerie();
                break;
            case 2: buscarEpisodiosSerie();
                break;
            case 0:
                System.out.println("Encerrando...");
                break;
            default:
                System.out.println("Opção invalida!");
        }

    }
    private void buscarSerie() {
        DadosSerie serieDados = getSerie();
        System.out.println(serieDados);
    }

    private DadosSerie getSerie(){
        System.out.print("Digite a serie desejada: ");
        var serieEscolhida = sc.nextLine();

        var url = CORE_URL + serieEscolhida.replace(" ", "+") + API_KEY;
        var json = consumoApi.obterDados(url);

       return conversor.obterDados(json, DadosSerie.class);
    }

    private void buscarEpisodiosSerie() {
        DadosSerie serie = getSerie();
        List<DadosTemporada> temporadaList = new ArrayList<>();

        for (int i = 1; i <= serie.totalTemp(); i++) {
            var json = consumoApi.obterDados(CORE_URL + serie.titulo().replace(" ", "+") + "&Season=" + i + "&apikey=5ec35de3");
            DadosTemporada temporadaDados = conversor.obterDados(json, DadosTemporada.class);
            temporadaList.add(temporadaDados);
        }

        temporadaList.forEach(System.out::println);

    }


}
