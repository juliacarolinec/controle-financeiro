package com.julia.controlefinanceiro.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Transacao {

    @Id
    @GeneratedValue
    private Long id;
    private String descricao;
    private double valor;
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    private LocalDate data;

    public Transacao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
