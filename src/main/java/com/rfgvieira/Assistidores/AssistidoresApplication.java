package com.rfgvieira.Assistidores;

import com.rfgvieira.Assistidores.model.DadosSerie;
import com.rfgvieira.Assistidores.service.ConsumoApi;
import com.rfgvieira.Assistidores.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
public class AssistidoresApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AssistidoresApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var consumoApi = new ConsumoApi();
        var json = consumoApi.obterDados("https://www.omdbapi.com/?t=the+boys&apikey=5ec35de3");
        ConverteDados conversor = new ConverteDados();

        DadosSerie serieDados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(serieDados);

    }



}
