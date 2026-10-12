package com.trokr.listener;

import com.trokr.event.TrocaConcluidaEvent;
import com.trokr.model.Usuario;
import com.trokr.repository.UsuarioRepository;
import com.trokr.service.CalculadoraCreditoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtribuirCreditosListener {

    private final CalculadoraCreditoService calculadora;
    private final UsuarioRepository usuarioRepository;

    @EventListener
    public void aoConcluirTroca(TrocaConcluidaEvent evento) {
        // Usuário A ganha créditos pelo Item A
        Usuario usuarioA = evento.getUsuarioA();
        int creditosA = calculadora.calcular(evento.getItemA());
        
        // CORRIDA 2: UPDATE Atômico (Sai o read-modify-write, entra a delegação para o banco)
        usuarioRepository.adicionarCreditos(usuarioA.getId(), creditosA);

        // Usuário B ganha créditos pelo Item B
        Usuario usuarioB = evento.getUsuarioB();
        int creditosB = calculadora.calcular(evento.getItemB());
        
        // CORRIDA 2: UPDATE Atômico
        usuarioRepository.adicionarCreditos(usuarioB.getId(), creditosB);
    }
}