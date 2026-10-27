package com.trokr.config;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

/**
 * 
 * Componente utilitário exclusivo para demonstração didática de condições de
 * corrida em aula.
 *
 * 
 * 
 * <p>
 * Permite injetar pausas artificiais controladas (via propriedade
 * {@code trokr.demo.atraso-ms}
 * ou variável {@code TROKR_DEMO_ATRASOMS}) para alargar janelas de concorrência
 * e tornar
 * bugs como lost updates e inconsistências de estado reproduzíveis.
 * </p>
 *
 * 
 * 
 * <p>
 * <b>Importante:</b> Em ambiente de produção o valor deve ser sempre 0 (zero),
 * não causando qualquer impacto no tempo de resposta da aplicação.
 * </p>
 * 
 */

@Component

public class AtrasoDemo {

    private final long atrasoMs;

    public AtrasoDemo(@Value("${trokr.demo.atraso-ms:0}") long atrasoMs) {

        this.atrasoMs = atrasoMs;

    }

    /**
     * 
     * Aplica o atraso configurado se for maior que zero.
     * Caso contrário, retorna imediatamente.
     * 
     */

    public void aplicar() {

        if (atrasoMs <= 0) {

            return;

        }

        try {

            Thread.sleep(atrasoMs);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        }

    }

    public long getAtrasoMs() {

        return atrasoMs;

    }

}