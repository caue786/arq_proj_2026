package com.trokr.model.state.proposta;

import com.trokr.model.Proposta;
import com.trokr.model.state.Status;

public class EstadoAtiva implements EstadoProposta {

    // --- TRANSIÇÕES VÁLIDAS ---
    @Override
    public void iniciarNegociacao(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoNegociado(), Status.NEGOCIADO);
    }

    @Override
    public void voltar(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoRascunho(), Status.RASCUNHO);
    }

    @Override
    public void cancelar(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoCancelado(), Status.CANCELADO);
    }

    // --- TRANSIÇÕES INVÁLIDAS ---
    @Override
    public void solicitarHomologacao(Proposta proposta) { throw new IllegalStateException("Proposta já está ativa."); }
    @Override
    public void aprovarHomologacao(Proposta proposta) { throw new IllegalStateException("Proposta já está ativa."); }
    @Override
    public void recusarHomologacao(Proposta proposta) { throw new IllegalStateException("Proposta já está ativa."); }
    @Override
    public void finalizarAcordo(Proposta proposta) { throw new IllegalStateException("Inicie a negociação antes de finalizar o acordo."); }
    @Override
    public void enviarContraproposta(Proposta proposta) { throw new IllegalStateException("Ação inválida para proposta raiz."); }
    @Override
    public void aceitarParaNegociacao(Proposta proposta) { throw new IllegalStateException("Ação inválida para proposta raiz."); }
    @Override
    public void recusarContraproposta(Proposta proposta) { throw new IllegalStateException("Ação inválida para proposta raiz."); }
}