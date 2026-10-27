package com.trokr.model.state.contraproposta;

import com.trokr.exception.TransicaoInvalidaException;
import com.trokr.model.Proposta;
import com.trokr.model.state.proposta.EstadoProposta;

public class EstadoRecusado implements EstadoProposta { 

    @Override
    public void solicitarHomologacao(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Esta contraproposta está encerrada."); }
    @Override
    public void aprovarHomologacao(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Esta contraproposta está encerrada."); }
    @Override
    public void recusarHomologacao(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Esta contraproposta está encerrada."); }
    @Override
    public void voltar(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Esta contraproposta está encerrada."); }
    @Override
    public void iniciarNegociacao(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Esta contraproposta está encerrada."); }
    @Override
    public void enviarContraproposta(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Esta contraproposta está encerrada."); }
    @Override
    public void aceitarParaNegociacao(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Esta contraproposta está encerrada."); }
    @Override
    public void recusarContraproposta(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Esta contraproposta está encerrada."); }
    @Override
    public void finalizarAcordo(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Esta contraproposta está encerrada."); }
    @Override
    public void cancelar(Proposta proposta) { throw new TransicaoInvalidaException("PROPOSTA_TRANSICAO_INVALIDA", "Esta contraproposta está encerrada."); }
}