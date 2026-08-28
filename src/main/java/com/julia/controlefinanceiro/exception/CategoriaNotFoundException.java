package com.julia.controlefinanceiro.exception;

public class CategoriaNotFoundException extends RuntimeException{
    public  CategoriaNotFoundException(String mensagem){
        super(mensagem);
    }
}
