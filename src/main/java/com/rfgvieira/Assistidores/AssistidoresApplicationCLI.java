//package com.rfgvieira.Assistidores;
//
//import com.rfgvieira.Assistidores.principal.Principal;
//import com.rfgvieira.Assistidores.repository.SerieRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class AssistidoresApplicationCLI implements CommandLineRunner {
//    @Autowired
//    SerieRepository serieRepositorio;
//    public static void main(String[] args) {
//        SpringApplication.run(AssistidoresApplicationCLI.class, args);
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        Principal principal = new Principal(serieRepositorio);
//        principal.exibirMenu();
//
//
//
//
//
//    }
//
//
//
//}
