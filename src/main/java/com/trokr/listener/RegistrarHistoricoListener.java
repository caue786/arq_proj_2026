package com.trokr.listener;

import com.trokr.event.TrocaConcluidaEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class RegistrarHistoricoListener {

    @TransactionalEventListener
    public void aoConcluirTroca(TrocaConcluidaEvent evento) {
        // Aqui entra a lógica para registar no histórico se já tiveres o repositório configurado
        System.out.println("HISTÓRICO: Registo criado para a proposta ID " + evento.getPropostaId());
    }
}