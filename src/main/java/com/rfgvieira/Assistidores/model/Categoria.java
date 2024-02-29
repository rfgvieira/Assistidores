package com.rfgvieira.Assistidores.model;

public enum Categoria {
    ACAO("Action", "Ação"),
    COMEDIA("Comedy", "Comédia"),
    ROMANCE("Romance", "Romance"),
    DRAMA("Drama", "Drama"),
    CRIME("Crime", "Crime"),
    DOCUMENTARIO("Documentary", "Documentário");

    private String categoria;
    private String categoriaPort;


    Categoria(String categoria, String categoriaPort) {
        this.categoria = categoria;
        this.categoriaPort = categoriaPort;
    }

    public static Categoria fromString(String text){
        for(Categoria cat : Categoria.values()){
            if(cat.categoria.equalsIgnoreCase(text)){
                return cat;
            }
        }
        throw new IllegalArgumentException("Nenhuma Categoria Encontrada para a String fornecida");
    }

    public static Categoria fromPort(String text){
        for(Categoria cat : Categoria.values()){
            if(cat.categoriaPort.equalsIgnoreCase(text)){
                return cat;
            }
        }
        throw new IllegalArgumentException("Nenhuma Categoria Encontrada para a String fornecida");
    }
}


