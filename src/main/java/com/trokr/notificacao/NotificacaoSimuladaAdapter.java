package com.trokr.notificacao;

import com.trokr.exception.NotificacaoIndisponivelException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class NotificacaoSimuladaAdapter implements NotificacaoPort {

    private static final Logger log = LoggerFactory.getLogger(NotificacaoSimuladaAdapter.class);

    private final SimulacaoNotificacao simulacao;
    private final double taxaFalha;
    private final long latenciaMs;

    @Override
    public void notificar(Long usuarioId, String mensagem) {
        // Se o interruptor estiver ligado (fora do ar), lança a exceção imediatamente
        if (simulacao.foraDoAr()) {
            throw new NotificacaoIndisponivelException("Serviço de notificação fora do ar");
        }

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