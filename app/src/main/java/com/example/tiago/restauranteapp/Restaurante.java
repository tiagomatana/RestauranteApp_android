package com.example.tiago.restauranteapp;

import java.io.Serializable;

/**
 * Created by Tiago on 08/05/2017.
 */

public class Restaurante implements Serializable {

    private Long id;
    private String nome;
    private String endereco;
    private String tipo;


    public Restaurante(){}


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

