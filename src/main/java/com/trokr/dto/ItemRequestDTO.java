package com.trokr.dto;

import com.trokr.model.CategoriaItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Dados de entrada para criar/atualizar um Item.
 * O item precisa informar o id do usuário dono e a categoria para o cálculo de créditos.
 */
public record ItemRequestDTO(

        @NotBlank(message = "titulo é obrigatório")
        String titulo,

        @NotBlank(message = "descricao é obrigatória")
        String descricao,

        @NotNull(message = "categoria é obrigatória")
        CategoriaItem categoria,

        @NotNull(message = "usuarioId é obrigatório")
        Long usuarioId
) {
}