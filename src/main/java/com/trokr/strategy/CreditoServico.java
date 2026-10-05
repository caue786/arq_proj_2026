package com.trokr.strategy;

import com.trokr.model.CategoriaItem;
import com.trokr.model.Item;
import org.springframework.stereotype.Component;

@Component
public class CreditoServico implements EstrategiaCredito {
    @Override
    public CategoriaItem categoria() {
        return CategoriaItem.SERVICO;
    }

    @Override
    public int calcular(Item item) {
        return 15; // Exemplo para serviços
    }
}