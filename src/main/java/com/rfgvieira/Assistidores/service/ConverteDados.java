package com.rfgvieira.Assistidores.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rfgvieira.Assistidores.model.DadosSerie;

public class ConverteDados implements IConverteDados{
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T obterDados(String json, Class<T> tipo) {
        try {
            return mapper.readValue(json, tipo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
