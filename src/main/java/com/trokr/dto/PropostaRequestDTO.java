package com.trokr.dto;

public record PropostaRequestDTO(
    String descricao,
    Long usuarioId,
    Long itemId,
    // Se esse campo vier nulo, é Proposta Raiz. Se vier com ID, é Contraproposta!
    Long propostaAnteriorId 
) {}