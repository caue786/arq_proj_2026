package com.trokr.model.state.contraproposta;

import com.trokr.exception.TransicaoInvalidaException;
import com.trokr.model.Proposta;
import com.trokr.model.state.Status;
import com.trokr.model.state.proposta.EstadoProposta;

public class EstadoNegociadoContra implements EstadoProposta {

    // --- TRANSIÇÕES VÁLIDAS ---
    @Override
    public void finalizarAcordo(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoFinalizadoContra(), Status.FINALIZADO);
    }

    @Override
    public void recusarContraproposta(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoRecusado(), Status.RECUSADO);
    }

    // --- TRANSIÇÕES INVÁLIDAS ---
    @Override
    public void solicitarHomologacao(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Ação exclusiva da raiz."); }
    @Override
    public void aprovarHomologacao(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Ação exclusiva da raiz."); }
    @Override
    public void recusarHomologacao(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Ação exclusiva da raiz."); }
    @Override
    public void voltar(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Ação exclusiva da raiz."); }
    @Override
    public void iniciarNegociacao(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Ação exclusiva da raiz."); }
    
    @Override
    public void enviarContraproposta(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "A contraproposta já está em negociação."); }
    @Override
    public void aceitarParaNegociacao(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "A contraproposta já está em negociação."); }
    @Override
    public void cancelar(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Durante a negociação, use a opção de recusar/desistir."); }
}