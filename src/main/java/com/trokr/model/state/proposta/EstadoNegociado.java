package com.trokr.model.state.proposta;

import com.trokr.model.Proposta;
import com.trokr.model.state.Status;

public class EstadoNegociado implements EstadoProposta {

    // --- TRANSIÇÕES VÁLIDAS ---
    @Override
    public void finalizarAcordo(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoFinalizado(), Status.FINALIZADO);
    }

    @Override
    public void voltar(Proposta proposta) {
        // Se a negociação falhar, o diagrama mostra que ela volta para ATIVA
        proposta.mudarEstadoPara(new EstadoAtiva(), Status.ATIVA);
    }

    @Override
    public void cancelar(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoCancelado(), Status.CANCELADO);
    }

    // --- TRANSIÇÕES INVÁLIDAS ---
    @Override
    public void solicitarHomologacao(Proposta proposta) { throw new IllegalStateException("Operação inválida no estado NEGOCIADO."); }
    @Override
    public void aprovarHomologacao(Proposta proposta) { throw new IllegalStateException("Operação inválida no estado NEGOCIADO."); }
    @Override
    public void recusarHomologacao(Proposta proposta) { throw new IllegalStateException("Operação inválida no estado NEGOCIADO."); }
    @Override
    public void iniciarNegociacao(Proposta proposta) { throw new IllegalStateException("A proposta já está em negociação."); }
    @Override
    public void enviarContraproposta(Proposta proposta) { throw new IllegalStateException("Ação inválida para proposta raiz."); }
    @Override
    public void aceitarParaNegociacao(Proposta proposta) { throw new IllegalStateException("Ação inválida para proposta raiz."); }
    @Override
    public void recusarContraproposta(Proposta proposta) { throw new IllegalStateException("Ação inválida para proposta raiz."); }
}