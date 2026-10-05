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
        // Usuário A ganha créditos pelo Item A (que ele ofereceu)
        Usuario usuarioA = evento.getProposta().getUsuario();
        int creditosA = calculadora.calcular(evento.getProposta().getItem());
        usuarioA.setSaldoCreditos(usuarioA.getSaldoCreditos() + creditosA);
        usuarioRepository.save(usuarioA);

        // Usuário B ganha créditos pelo Item B (da contraproposta)
        Usuario usuarioB = evento.ContrapropostaAceita().getUsuario();
        int creditosB = calculadora.calcular(evento.ContrapropostaAceita().getItem());
        usuarioB.setSaldoCreditos(usuarioB.getSaldoCreditos() + creditosB);
        usuarioRepository.save(usuarioB);
    }
}