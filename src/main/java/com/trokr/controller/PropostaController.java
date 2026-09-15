package com.trokr.controller;

import com.trokr.dto.PropostaRequestDTO;
import com.trokr.dto.PropostaResponseDTO;
import com.trokr.model.Proposta;
import com.trokr.service.PropostaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propostas")
public class PropostaController {

    @Autowired
    private PropostaService propostaService;

    // --- CRIAÇÃO E BUSCA (Mantido o excelente trabalho dela) ---

    @PostMapping
    public ResponseEntity<PropostaResponseDTO> criar(@Valid @RequestBody PropostaRequestDTO dto) {
        Proposta salva = propostaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(PropostaResponseDTO.fromEntity(salva));
    }

    @PostMapping("/{id}/contraproposta")
    public ResponseEntity<PropostaResponseDTO> criarContraproposta(
            @PathVariable Long id, 
            @Valid @RequestBody PropostaRequestDTO dto) {
        Proposta contraproposta = propostaService.criarContraproposta(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(PropostaResponseDTO.fromEntity(contraproposta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropostaResponseDTO> buscarPorId(@PathVariable Long id) {
        Proposta proposta = propostaService.buscarPorId(id);
        return ResponseEntity.ok(PropostaResponseDTO.fromEntity(proposta));
    }

    @GetMapping
    public ResponseEntity<List<PropostaResponseDTO>> listarTodas() {
        List<PropostaResponseDTO> propostas = propostaService.listarTodas()
                .stream()
                .map(PropostaResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(propostas);
    }

    // --- TRANSIÇÕES DE ESTADO (Corrigido para @PatchMapping) ---

    @PatchMapping("/{id}/solicitar-homologacao")
    public ResponseEntity<Void> solicitarHomologacao(@PathVariable Long id) {
        propostaService.solicitarHomologacao(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/aprovar-homologacao")
    public ResponseEntity<Void> aprovarHomologacao(@PathVariable Long id) {
        propostaService.aprovarHomologacao(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/recusar-homologacao")
    public ResponseEntity<Void> recusarHomologacao(@PathVariable Long id) {
        propostaService.recusarHomologacao(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/iniciar-negociacao")
    public ResponseEntity<Void> iniciarNegociacao(@PathVariable Long id) {
        propostaService.iniciarNegociacao(id);
        return ResponseEntity.noContent().build();
    }

    // --- TRANSIÇÕES DA CONTRAPROPOSTA ---

    @PatchMapping("/{id}/enviar-contraproposta")
    public ResponseEntity<Void> enviarContraproposta(@PathVariable Long id) {
        propostaService.enviarContraproposta(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/aceitar-contraproposta")
    public ResponseEntity<Void> aceitarContraproposta(@PathVariable Long id) {
        propostaService.aceitarParaNegociacao(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/recusar-contraproposta")
    public ResponseEntity<Void> recusarContraproposta(@PathVariable Long id) {
        propostaService.recusarContraproposta(id);
        return ResponseEntity.noContent().build();
    }

    // --- TRANSIÇÕES FINAIS ---

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        propostaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/finalizar-acordo")
    public ResponseEntity<Void> finalizarAcordo(@PathVariable Long id) {
        propostaService.finalizarAcordo(id);
        return ResponseEntity.noContent().build();
    }
}