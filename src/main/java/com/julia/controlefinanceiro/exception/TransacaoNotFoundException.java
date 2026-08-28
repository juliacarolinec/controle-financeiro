package com.julia.controlefinanceiro.exception;

public class TransacaoNotFoundException extends RuntimeException{
    public TransacaoNotFoundException(String mensagem){
        super(mensagem);
    }
}
