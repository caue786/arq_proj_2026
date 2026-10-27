package com.trokr.listener;

import com.trokr.event.TrocaConcluidaEvent;
import com.trokr.exception.NotificacaoIndisponivelException;
import com.trokr.service.NotificacaoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificarUsuariosListener {

    private static final Logger log = LoggerFactory.getLogger(NotificarUsuariosListener.class);
    private static final int MAX_TENTATIVAS = 3;
    private static final long ESPERA_INICIAL_MS = 200;

    private final NotificacaoService notificacaoService;

    @TransactionalEventListener
    public void aoConcluirTroca(TrocaConcluidaEvent evento) {
        String mensagem = "Sua troca foi concluída com sucesso!";

        // Notifica o Utilizador A com retry
        enviarComRetry(evento.getUsuarioA().getId(), mensagem);

        // Notifica o Utilizador B com retry
        // (Garante que se o A falhar esgotando as tentativas, o B ainda é tentado)
        enviarComRetry(evento.getUsuarioB().getId(), mensagem);
    }

    private void enviarComRetry(Long usuarioId, String mensagem) {
        long espera = ESPERA_INICIAL_MS;
        for (int tentativa = 1; tentativa <= MAX_TENTATIVAS; tentativa++) {
            try {
                notificacaoService.enviar(usuarioId, mensagem);
                return; // Sucesso: sai do método de envio
            } catch (NotificacaoIndisponivelException ex) {
                if (tentativa == MAX_TENTATIVAS) {
                    // Esgotadas as tentativas: regista o erro técnico mas NÃO propaga,
                    // pois a troca já foi concluída e comitada com sucesso na base de dados
                    log.error("Falha definitiva ao enviar notificação para o utilizador {} após 3 tentativas.",
                            usuarioId);
                    return;
                }

                log.warn("Tentativa {} falhou para o utilizador {}. Nova tentativa em {} ms", tentativa, usuarioId,
                        espera);

                try {
                    Thread.sleep(espera);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                espera *= 2; // Duplica o tempo de espera (backoff exponencial)
            }
        }
    }
}