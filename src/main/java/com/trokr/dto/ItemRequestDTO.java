package com.trokr.dto;

import com.trokr.model.CategoriaItem;
import com.trokr.model.NivelExperiencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemRequestDTO(

                @NotBlank(message = "titulo é obrigatório") String titulo,

                @NotBlank(message = "descricao é obrigatória") String descricao,

                @NotNull(message = "categoria é obrigatória") CategoriaItem categoria,

                @NotNull(message = "usuarioId é obrigatório") Long usuarioId,

                // Campos específicos da Aula 12 (todos opcionais/anuláveis)[cite: 14]
                Integer pesoGramas,
                Integer duracaoMinutos,
                NivelExperiencia nivel) {
}