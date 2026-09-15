package com.trokr.model.state.contraproposta;

import com.trokr.model.Proposta;
import com.trokr.model.state.Status;
import com.trokr.model.state.proposta.EstadoProposta;

public class EstadoRascunhoContra implements EstadoProposta {

    // --- TRANSIÇÕES VÁLIDAS ---
    @Override
    public void enviarContraproposta(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoEm_Analise(), Status.EM_ANALISE);
    }

    @Override
    public void cancelar(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoCanceladoContra(), Status.CANCELADO);
    }

    // --- TRANSIÇÕES INVÁLIDAS ---
    @Override
    public void solicitarHomologacao(Proposta proposta) { throw new IllegalStateException("Ação exclusiva da proposta raiz."); }
    @Override
    public void aprovarHomologacao(Proposta proposta) { throw new IllegalStateException("Ação exclusiva da proposta raiz."); }
    @Override
    public void recusarHomologacao(Proposta proposta) { throw new IllegalStateException("Ação exclusiva da proposta raiz."); }
    @Override
    public void voltar(Proposta proposta) { throw new IllegalStateException("Ação exclusiva da proposta raiz."); }
    @Override
    public void iniciarNegociacao(Proposta proposta) { throw new IllegalStateException("Ação exclusiva da proposta raiz."); }
    
    @Override
    public void aceitarParaNegociacao(Proposta proposta) { throw new IllegalStateException("A contraproposta precisa ser enviada primeiro."); }
    @Override
    public void recusarContraproposta(Proposta proposta) { throw new IllegalStateException("A contraproposta precisa ser enviada primeiro."); }
    @Override
    public void finalizarAcordo(Proposta proposta) { throw new IllegalStateException("Não é possível finalizar acordo no rascunho."); }
}