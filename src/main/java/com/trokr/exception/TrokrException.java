package com.trokr.exception;

public abstract class TrokrException extends RuntimeException {
    
    private final String codigo;

    protected TrokrException(String codigo, String mensagem) {
        super(mensagem);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}