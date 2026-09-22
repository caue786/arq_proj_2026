package com.trokr.service;

import com.trokr.model.LogEvento;
import com.trokr.repository.LogEventoRepository;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class LogEventoService {

    private final LogEventoRepository logEventoRepository;

    public LogEventoService(LogEventoRepository logEventoRepository) {
        this.logEventoRepository = logEventoRepository;
    }

    public void registrarInfo(String origem, String mensagem, Map<String, Object> payload) {
        LogEvento log = new LogEvento("INFO_GERAL", "INFO", origem, payload, null);
        log.getPayload().put("mensagemPrincipal", mensagem);
        logEventoRepository.insert(log);
    }

    public void registrarErro(String origem, String mensagem, Exception excecao) {
        Map<String, Object> payload = Map.of(
            "erro", excecao.getClass().getName(),
            "detalhes", excecao.getMessage()
        );
        LogEvento log = new LogEvento("ERRO_SISTEMA", "ERROR", origem, payload, null);
        log.getPayload().put("mensagemPrincipal", mensagem);
        logEventoRepository.insert(log);
    }
}