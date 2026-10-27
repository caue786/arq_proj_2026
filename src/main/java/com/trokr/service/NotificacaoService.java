package com.trokr.service;

import com.trokr.exception.NotificacaoIndisponivelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {

    private static final Logger log = LoggerFactory.getLogger(NotificacaoService.class);

    @Value("${trokr.notificacao.taxa-falha:0.0}")
    private double taxaFalha;

    @Value("${trokr.notificacao.latencia-ms:0}")
    private long latenciaMs;

    public void enviar(Long usuarioId, String mensagem) {
        // Simula a latência de rede configurada
        try {
            Thread.sleep(latenciaMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Sorteia contra a taxa de falha para simular indisponibilidade temporária
        if (Math.random() < taxaFalha) {
            throw new NotificacaoIndisponivelException("O serviço de notificação está temporariamente indisponível.");
        }

        log.info("Notificação enviada com sucesso para o utilizador {}: {}", usuarioId, mensagem);
    }
}