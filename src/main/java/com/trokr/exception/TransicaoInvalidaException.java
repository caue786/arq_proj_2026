package com.trokr.exception;

public class TransicaoInvalidaException extends TrokrException {
    public TransicaoInvalidaException(String codigo, String mensagem) {
        super(codigo, mensagem);
    }
}