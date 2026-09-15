package com.trokr.model.state.proposta;

import com.trokr.model.Proposta;
import com.trokr.model.state.Status;

public class EstadoHomologacao implements EstadoProposta {

    // --- TRANSIÇÕES VÁLIDAS ---
    @Override
    public void aprovarHomologacao(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoAtiva(), Status.ATIVA);
    }

    @Override
    public void recusarHomologacao(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoRascunho(), Status.RASCUNHO);
    }

    @Override
    public void cancelar(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoCancelado(), Status.CANCELADO);
    }

    // --- TRANSIÇÕES INVÁLIDAS ---
    @Override
    public void solicitarHomologacao(Proposta proposta) { throw new IllegalStateException("A proposta já está em homologação."); }
    @Override
    public void voltar(Proposta proposta) { throw new IllegalStateException("Use recusarHomologacao para voltar ao rascunho."); }
    @Override
    public void iniciarNegociacao(Proposta proposta) { throw new IllegalStateException("A proposta precisa ser aprovada antes de negociar."); }
    @Override
    public void finalizarAcordo(Proposta proposta) { throw new IllegalStateException("Ação inválida."); }
    @Override
    public void enviarContraproposta(Proposta proposta) { throw new IllegalStateException("Ação inválida para proposta raiz."); }
    @Override
    public void aceitarParaNegociacao(Proposta proposta) { throw new IllegalStateException("Ação inválida para proposta raiz."); }
    @Override
    public void recusarContraproposta(Proposta proposta) { throw new IllegalStateException("Ação inválida para proposta raiz."); }
}