package com.trokr.model.state.contraproposta;

import com.trokr.exception.TransicaoInvalidaException;
import com.trokr.model.Proposta;
import com.trokr.model.state.Status;
import com.trokr.model.state.proposta.EstadoProposta;

public class EstadoEm_Analise implements EstadoProposta {

    // --- TRANSIÇÕES VÁLIDAS ---
    @Override
    public void aceitarParaNegociacao(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoNegociadoContra(), Status.NEGOCIADO);
        proposta.getPropostaAnterior().iniciarNegociacao();
        proposta.getPropostaAnterior().recusarDemaisContrapropostas(proposta);
    }

    @Override
    public void recusarContraproposta(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoRecusado(), Status.RECUSADO);
    }

    @Override
    public void cancelar(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoCanceladoContra(), Status.CANCELADO);
    }

    // --- TRANSIÇÕES INVÁLIDAS ---
    @Override
    public void solicitarHomologacao(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Ação exclusiva da proposta raiz."); }
    @Override
    public void aprovarHomologacao(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Ação exclusiva da proposta raiz."); }
    @Override
    public void recusarHomologacao(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Ação exclusiva da proposta raiz."); }
    @Override
    public void voltar(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Ação exclusiva da proposta raiz."); }
    @Override
    public void iniciarNegociacao(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Ação exclusiva da proposta raiz."); }
    
    @Override
    public void enviarContraproposta(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "A contraproposta já foi enviada."); }
    @Override
    public void finalizarAcordo(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Precisa ser aceita para negociação antes de finalizar o acordo."); }
}