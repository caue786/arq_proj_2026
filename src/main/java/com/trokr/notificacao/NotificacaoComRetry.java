package com.trokr.notificacao;

import com.trokr.exception.NotificacaoIndisponivelException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class NotificacaoComRetry implements NotificacaoPort {
    
    private final NotificacaoPort proximo;
    private final int maxTentativas;
    private final long esperaInicialMs;

    private void dormir(long ms) {
        try { 
            Thread.sleep(ms); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void notificar(Long usuarioId, String mensagem) {
        long espera = esperaInicialMs;
        
        for (int tentativa = 1;; tentativa++) {
            try {
                proximo.notificar(usuarioId, mensagem);
                return;
            } catch (NotificacaoIndisponivelException ex) {
                if (tentativa >= maxTentativas) {
                    throw ex;
                }
                log.warn("Tentativa {} para {} falhou", tentativa, usuarioId);
            }
            dormir(espera);
            espera *= 2;
        }
    }
}