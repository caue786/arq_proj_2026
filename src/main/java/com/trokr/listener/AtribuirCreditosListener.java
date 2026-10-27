package com.trokr.listener;

import com.trokr.event.TrocaConcluidaEvent;
import com.trokr.model.Item;
import com.trokr.model.Usuario;
import com.trokr.repository.ItemRepository;
import com.trokr.repository.UsuarioRepository;
import com.trokr.service.CalculadoraCreditoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AtribuirCreditosListener {

    private final CalculadoraCreditoService calculadora;
    private final UsuarioRepository usuarioRepository;
    private final ItemRepository itemRepository;

    @EventListener
    @Transactional
    public void aoConcluirTroca(TrocaConcluidaEvent evento) {
        // Recarrega os itens dentro da sessão transacional ativa para evitar LazyInitializationException
        Item itemA = itemRepository.findById(evento.getItemA().getId()).orElseThrow();
        Item itemB = itemRepository.findById(evento.getItemB().getId()).orElseThrow();

        // Usuário A ganha créditos pelo Item A
        Usuario usuarioA = evento.getUsuarioA();
        int creditosA = calculadora.calcular(itemA);
        usuarioRepository.adicionarCreditos(usuarioA.getId(), creditosA);

        // Usuário B ganha créditos pelo Item B
        Usuario usuarioB = evento.getUsuarioB();
        int creditosB = calculadora.calcular(itemB);
        usuarioRepository.adicionarCreditos(usuarioB.getId(), creditosB);
    }
}