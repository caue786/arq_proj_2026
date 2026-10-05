package com.trokr.service;

import com.trokr.model.CategoriaItem;
import com.trokr.model.Item;
import com.trokr.strategy.EstrategiaCredito;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalculadoraCreditoService {

    private final Map<CategoriaItem, EstrategiaCredito> estrategias;

    public CalculadoraCreditoService(List<EstrategiaCredito> todasAsEstrategias) {
        this.estrategias = todasAsEstrategias.stream()
                .collect(Collectors.toMap(EstrategiaCredito::categoria, e -> e));
    }

    public int calcular(Item item) {
        EstrategiaCredito estrategia = estrategias.get(item.getCategoria());
        if (estrategia == null) {
            throw new IllegalStateException("Nenhuma estratégia encontrada para a categoria: " + item.getCategoria());
        }
        return estrategia.calcular(item);
    }
}