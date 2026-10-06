package com.trokr.service;

import com.trokr.model.LogEvento;
import com.trokr.repository.LogEventoRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap; // <-- Adiciona este import
import java.util.Map;

@Service
public class LogEventoService {

    private final LogEventoRepository logEventoRepository;

    public LogEventoService(LogEventoRepository logEventoRepository) {
        this.logEventoRepository = logEventoRepository;
    }

    public void registrarInfo(String origem, String mensagem, Map<String, Object> payload) {
        Map<String, Object> payloadMutavel = payload != null ? new HashMap<>(payload) : new HashMap<>();
        payloadMutavel.put("mensagemPrincipal", mensagem);
        
        LogEvento log = new LogEvento("INFO_GERAL", "INFO", origem, payloadMutavel, null);
        logEventoRepository.insert(log);
    }

    public void registrarErro(String origem, String mensagem, Exception excecao) {
        Map<String, Object> payload = new HashMap<>(Map.of(
            "erro", excecao.getClass().getName(),
            "detalhes", excecao.getMessage() != null ? excecao.getMessage() : "Sem mensagem"
        ));
        
        payload.put("mensagemPrincipal", mensagem);
        
        LogEvento log = new LogEvento("ERRO_SISTEMA", "ERROR", origem, payload, null);
        logEventoRepository.insert(log);
    }
}