package com.trokr.dto;

import com.trokr.model.Item;
import com.trokr.model.CategoriaItem;
import java.time.LocalDateTime;

/**
 * Dados de saída de um Item, incluindo a categoria e dados do dono achatados.
 */
public record ItemResponseDTO(
        Long id,
        String titulo,
        String descricao,
        CategoriaItem categoria,
        Long usuarioId,
        String usuarioNome,
        LocalDateTime dataCriacao
) {

    public static ItemResponseDTO fromEntity(Item item) {
        return new ItemResponseDTO(
                item.getId(),
                item.getTitulo(),
                item.getDescricao(),
                item.getCategoria(),
                item.getUsuarioProprietario().getId(),
                item.getUsuarioProprietario().getNome(),
                item.getDataCriacao()
        );
    }
}