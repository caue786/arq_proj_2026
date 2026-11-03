package com.trokr.listener;

import com.trokr.event.TrocaConcluidaEvent;
import com.trokr.model.LogEvento;
import com.trokr.model.Usuario;
import com.trokr.notificacao.NotificacaoPort;
import com.trokr.repository.LogEventoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificarUsuariosListener {

    private static final Logger log = LoggerFactory.getLogger(NotificarUsuariosListener.class);

    private final NotificacaoPort notificacaoPort;
    private final LogEventoRepository logEventoRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void aoConcluirTroca(TrocaConcluidaEvent evento) {
        String mensagem = "Sua troca da proposta " + evento.getPropostaId() + " foi concluida.";

        for (Usuario usuario : List.of(evento.getUsuarioA(), evento.getUsuarioB())) {
            Long usuarioId = usuario.getId();
            try {
                notificacaoPort.notificar(usuarioId, mensagem);
            } catch (Exception ex) {
                log.error("Notificação para o usuário {} falhou", usuarioId, ex);

                LogEvento logEvento = new LogEvento(
                        "FALHA_NOTIFICACAO",
                        "ERROR",
                        "NotificarUsuariosListener",
                        Map.of(
                                "mensagemErro", ex.getMessage() != null ? ex.getMessage() : "Sem mensagem de erro",
                                "propostaId", evento.getPropostaId()),
                        usuarioId);

                logEventoRepository.save(logEvento);
            }
        }
    }
}