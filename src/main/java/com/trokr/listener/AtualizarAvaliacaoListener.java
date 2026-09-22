package com.trokr.listener;

import com.trokr.event.TrocaConcluidaEvent;
import com.trokr.model.Avaliacao;
import com.trokr.model.StatusAvaliacao;
import com.trokr.repository.AvaliacaoRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AtualizarAvaliacaoListener {

    private final AvaliacaoRepository avaliacaoRepository;

    public AtualizarAvaliacaoListener(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    @EventListener
    public void aoConcluirTroca(TrocaConcluidaEvent evento) {
        // Direção 1: UsuarioA avalia UsuarioB
        Avaliacao avaliacao1 = new Avaliacao();
        avaliacao1.setAvaliador(evento.getUsuarioA());
        avaliacao1.setAvaliado(evento.getUsuarioB());
        avaliacao1.setStatus(StatusAvaliacao.PENDENTE);

        // Direção 2: UsuarioB avalia UsuarioA
        Avaliacao avaliacao2 = new Avaliacao();
        avaliacao2.setAvaliador(evento.getUsuarioB());
        avaliacao2.setAvaliado(evento.getUsuarioA());
        avaliacao2.setStatus(StatusAvaliacao.PENDENTE);

        avaliacaoRepository.save(avaliacao1);
        avaliacaoRepository.save(avaliacao2);
    }
}