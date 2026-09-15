package com.trokr.model.state;

public enum Status {
    RASCUNHO,
    HOMOLOGACAO,
    ATIVA,
    NEGOCIADO,
    FINALIZADO,
    CANCELADO,
    EM_ANALISE, // Exclusivo da contraproposta
    RECUSADO    // Exclusivo da contraproposta
}