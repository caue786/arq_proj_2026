package com.trokr.adapter;

import com.trokr.model.LogEvento;
import com.trokr.repository.LogEventoRepository;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RegistroDeEventosMongo implements RegistroDeEventos {

    private final LogEventoRepository mongoRepository;

    public RegistroDeEventosMongo(LogEventoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public void registrar(String tipo, String mensagem, Object payload) {
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("mensagem", mensagem);
        payloadMap.put("detalhes", payload);

        LogEvento doc = new LogEvento(tipo, "INFO", "SistemaAdapter", payloadMap, null);
        mongoRepository.insert(doc);
    }

    @Override
    public List<Object> buscarPorTipo(String tipo) {
        return mongoRepository.findByTipo(tipo).stream()
                .map(doc -> (Object) doc)
                .collect(Collectors.toList());
    }
}