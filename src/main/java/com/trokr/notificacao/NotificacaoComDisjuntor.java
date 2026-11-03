package com.trokr.notificacao;

import com.trokr.exception.NotificacaoIndisponivelException;
import com.trokr.model.LogEvento;
import com.trokr.repository.LogEventoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class NotificacaoComDisjuntor implements NotificacaoPort {

    private final NotificacaoPort proximo;
    private final LogEventoRepository logEventoRepository;
    private final int limiteFalhas;
    private final Duration tempoAberto;

    private int falhasSeguidas = 0;
    private Instant abertoAte = null;

    @Override
    public synchronized void notificar(Long usuarioId, String mensagem) {
        // Se o disjuntor estiver ABERTO e o tempo ainda não passou
        if (abertoAte != null && Instant.now().isBefore(abertoAte)) {
            log.warn("Disjuntor aberto: notificação para o usuário {} ficou pendente", usuarioId);
            registrarPendente(usuarioId, mensagem);
            return;
        }

        // Verifica se entrou no estado Meio-Aberto (o tempo passou)
        boolean meioAberto = abertoAte != null;

        try {
            proximo.notificar(usuarioId, mensagem);
            
            if (meioAberto) {
                log.info("Disjuntor FECHADO: o serviço voltou");
            }
            
            falhasSeguidas = 0;
            abertoAte = null;

        } catch (NotificacaoIndisponivelException ex) {
            if (meioAberto || ++falhasSeguidas >= limiteFalhas) {
                abertoAte = Instant.now().plus(tempoAberto);
                log.warn("Disjuntor ABERTO até {}", abertoAte);
            }
            throw ex;
        }
    }

    private void registrarPendente(Long usuarioId, String mensagem) {
        try {
            LogEvento logEvento = new LogEvento(
                    "NOTIFICACAO_PENDENTE",
                    "WARN",
                    "NotificacaoComDisjuntor",
                    Map.of(
                            "mensagem", mensagem,
                            "motivo", "Disjuntor aberto"
                    ),
                    usuarioId
            );
            logEventoRepository.save(logEvento);
        } catch (Exception ex) {
            log.error("Não foi possível registrar a pendência", ex);
        }
    }
}