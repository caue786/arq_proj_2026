package com.trokr.listener;

import com.trokr.event.TrocaConcluidaEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificarUsuariosListener {

    @TransactionalEventListener
    public void aoConcluirTroca(TrocaConcluidaEvent evento) {
        System.out.println("NOTIFICAÇÃO SIMULADA: Troca concluída entre " 
            + evento.getUsuarioA().getNome() + " e " + evento.getUsuarioB().getNome());
    }
}