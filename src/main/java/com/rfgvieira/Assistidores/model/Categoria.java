package com.rfgvieira.Assistidores.model;

public enum Categoria {
    ACAO("Action"),
    COMEDIA("Comedy"),
    ROMANCE("Romande"),
    DRAMA("Drama"),
    CRIME("Crime"),
    DOCUMENTARIO("Documentary");

    private String categoria;


    Categoria(String categoria) {
        this.categoria = categoria;
    }

    public static Categoria fromString(String text){
        for(Categoria cat : Categoria.values()){
            if(cat.categoria.equalsIgnoreCase(text)){
                return cat;
            }
        }
        throw new IllegalArgumentException("Nenhuma Categoria Encontrada para a String fornecida");
    }
}


