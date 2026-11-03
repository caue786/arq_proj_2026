package com.trokr.controller;

import com.trokr.model.LogEvento;
import com.trokr.repository.LogEventoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogEventoController {

    private final LogEventoRepository logEventoRepository;

    public LogEventoController(LogEventoRepository logEventoRepository) {
        this.logEventoRepository = logEventoRepository;
    }

    @GetMapping
    public List<LogEvento> listarLogs(@RequestParam(required = false) String tipo) {
        if (tipo == null || tipo.isBlank()) {
            return logEventoRepository.findAll(); // Retorna todos os logs se nenhum tipo for informado
        }
        return logEventoRepository.findByTipo(tipo); // Filtra pelo tipo se fornecido
    }
}