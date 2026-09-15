package com.trokr.model.state.proposta;

import com.trokr.model.Proposta;
import com.trokr.model.state.Status;

public class EstadoRascunho implements EstadoProposta {

    @Override
    public void solicitarHomologacao(Proposta proposta) {
        // Transição válida!
        proposta.mudarEstadoPara(new EstadoHomologacao(), Status.HOMOLOGACAO);
    }

    @Override
    public void cancelar(Proposta proposta) {
        // Transição válida!
        proposta.mudarEstadoPara(new EstadoCancelado(), Status.CANCELADO);
    }

    // Todos os outros métodos devem lançar erro, pois não podem acontecer no Rascunho
    @Override
    public void aprovarHomologacao(Proposta proposta) { throw new IllegalStateException("Não é possível aprovar homologação no estado RASCUNHO."); }
    @Override
    public void recusarHomologacao(Proposta proposta) { throw new IllegalStateException("Não é possível recusar homologação no estado RASCUNHO."); }
    @Override
    public void voltar(Proposta proposta) { throw new IllegalStateException("Não é possível voltar no estado RASCUNHO."); }
    @Override
    public void iniciarNegociacao(Proposta proposta) { throw new IllegalStateException("Não é possível iniciar negociação no estado RASCUNHO."); }
    @Override
    public void enviarContraproposta(Proposta proposta) { throw new IllegalStateException("Ação inválida para proposta raiz."); }
    @Override
    public void aceitarParaNegociacao(Proposta proposta) { throw new IllegalStateException("Ação inválida para proposta raiz."); }
    @Override
    public void recusarContraproposta(Proposta proposta) { throw new IllegalStateException("Ação inválida para proposta raiz."); }
    @Override
    public void finalizarAcordo(Proposta proposta) { throw new IllegalStateException("Ação inválida no estado RASCUNHO."); }
}