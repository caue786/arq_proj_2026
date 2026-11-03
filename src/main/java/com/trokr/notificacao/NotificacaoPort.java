package com.trokr.notificacao;

public interface NotificacaoPort {
    void notificar(Long usuarioId, String mensagem);
}