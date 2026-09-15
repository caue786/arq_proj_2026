package com.trokr.model.state.proposta;

import com.trokr.model.Proposta;

public interface EstadoProposta {
    // --- Ações da Proposta Raiz ---
    void solicitarHomologacao(Proposta proposta);
    void aprovarHomologacao(Proposta proposta);
    void recusarHomologacao(Proposta proposta);
    void voltar(Proposta proposta);
    void iniciarNegociacao(Proposta proposta);
    
    // --- Ações da Contraproposta (Deixe criado para usarmos depois) ---
    void enviarContraproposta(Proposta proposta);
    void aceitarParaNegociacao(Proposta proposta);
    void recusarContraproposta(Proposta proposta);
    
    // --- Ações Compartilhadas ---
    void finalizarAcordo(Proposta proposta);
    void cancelar(Proposta proposta);
}