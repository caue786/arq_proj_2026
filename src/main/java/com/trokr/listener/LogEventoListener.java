package com.trokr.listener;

import com.trokr.adapter.RegistroDeEventos;
import com.trokr.event.TrocaConcluidaEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LogEventoListener {

    private final RegistroDeEventos registroDeEventos;

    public LogEventoListener(RegistroDeEventos registroDeEventos) {
        this.registroDeEventos = registroDeEventos;
    }

    @EventListener
    public void onTrocaConcluida(TrocaConcluidaEvent event) {
        // Regista o evento no MongoDB através do adaptador poliglota
        registroDeEventos.registrar(
            "TROCA_CONCLUIDA",
            "Troca concluída com sucesso entre os utilizadores para a proposta ID: " + event.getPropostaId(),
            event
        );
    }
}