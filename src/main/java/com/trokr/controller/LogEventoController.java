package com.trokr.controller;

import com.trokr.adapter.RegistroDeEventos;
import com.trokr.model.LogEvento;
import com.trokr.repository.LogEventoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogEventoController {

    private final LogEventoRepository logEventoRepository;
    private final RegistroDeEventos registroDeEventos;

    public LogEventoController(LogEventoRepository logEventoRepository, RegistroDeEventos registroDeEventos) {
        this.logEventoRepository = logEventoRepository;
        this.registroDeEventos = registroDeEventos;
    }

    @GetMapping
    public List<LogEvento> listarPorTipo(@RequestParam String tipo) {
        return logEventoRepository.findByTipo(tipo);
    }
}