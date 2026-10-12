package com.trokr.listener;

import com.trokr.adapter.RegistroDeEventos;
import com.trokr.event.TrocaConcluidaEvent;
import org.springframework.stereotype.Component;
// Novos imports para a transação
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class LogEventoListener {

    private final RegistroDeEventos registroDeEventos;

    public LogEventoListener(RegistroDeEventos registroDeEventos) {
        this.registroDeEventos = registroDeEventos;
    }

    // --- CORRIDA 3: Efeito Fantasma ---
    // O evento só é processado depois que o commit no banco principal (Postgres) der sucesso.
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onTrocaConcluida(TrocaConcluidaEvent event) {
        // Regista o evento no MongoDB através do adaptador poliglota
        registroDeEventos.registrar(
            "TROCA_CONCLUIDA",
            "Troca concluída com sucesso entre os utilizadores para a proposta ID: " + event.getPropostaId(),
            event
        );
    }
}