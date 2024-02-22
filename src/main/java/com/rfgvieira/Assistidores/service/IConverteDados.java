package com.rfgvieira.Assistidores.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> tipo);
}
