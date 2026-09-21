package com.trokr.controller;

import com.trokr.model.Avaliacao;
import com.trokr.model.StatusAvaliacao;
import com.trokr.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    // Endpoint PATCH para preencher a avaliação pendente
    @PatchMapping("/{id}")
    public ResponseEntity<Avaliacao> avaliar(
            @PathVariable Long id, 
            @RequestBody Avaliacao avaliacaoRequest) {
        
        // 1. Busca a avaliação pendente pelo ID
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada com ID: " + id));
        
        // 2. Valida se já foi avaliada
        if (avaliacao.getStatus() != StatusAvaliacao.PENDENTE) {
            throw new IllegalStateException("Esta avaliação já foi preenchida anteriormente.");
        }
        
        // 3. Atualiza os dados com a nota e descrição enviadas
        avaliacao.setNota(avaliacaoRequest.getNota());
        avaliacao.setDescricao(avaliacaoRequest.getDescricao());
        avaliacao.setStatus(StatusAvaliacao.AVALIADA);
        
        // 4. Guarda no banco de dados
        Avaliacao salva = avaliacaoRepository.save(avaliacao);
        
        return ResponseEntity.ok(salva);
    }
}