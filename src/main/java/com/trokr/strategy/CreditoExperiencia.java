package com.trokr.strategy;

import com.trokr.model.CategoriaItem;
import com.trokr.model.Item;
import org.springframework.stereotype.Component;

@Component
public class CreditoExperiencia implements EstrategiaCredito {
    @Override
    public CategoriaItem categoria() {
        return CategoriaItem.EXPERIENCIA;
    }

    @Override
    public int calcular(Item item) {
        return 20; // Exemplo para experiências
    }
}