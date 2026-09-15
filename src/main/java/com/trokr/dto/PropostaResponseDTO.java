package com.trokr.dto;

import com.trokr.model.Proposta;
import com.trokr.model.state.Status;

public record PropostaResponseDTO(
    Long id,
    String descricao,
    Status status,
    Long usuarioId,
    Long itemId,
    Long propostaAnteriorId
) {
    // Método prático para converter a Entidade no DTO de resposta
    public static PropostaResponseDTO fromEntity(Proposta proposta) {
        return new PropostaResponseDTO(
            proposta.getId(),
            proposta.getDescricao(),
            proposta.getStatus(),
            proposta.getUsuario().getId(),
            proposta.getItem().getId(),
            proposta.isContraproposta() ? proposta.getPropostaAnterior().getId() : null
        );
    }
}