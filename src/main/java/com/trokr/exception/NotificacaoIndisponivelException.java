package com.trokr.exception;

public class NotificacaoIndisponivelException extends RuntimeException {
    public NotificacaoIndisponivelException(String mensagem) {
        super(mensagem);
    }
}