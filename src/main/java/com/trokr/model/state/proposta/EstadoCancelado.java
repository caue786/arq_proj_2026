package com.trokr.model.state.proposta;

import com.trokr.model.Proposta;

public class EstadoCancelado implements EstadoProposta {

    @Override
    public void solicitarHomologacao(Proposta proposta) { throw new IllegalStateException("Proposta CANCELADA não aceita alterações."); }
    @Override
    public void aprovarHomologacao(Proposta proposta) { throw new IllegalStateException("Proposta CANCELADA não aceita alterações."); }
    @Override
    public void recusarHomologacao(Proposta proposta) { throw new IllegalStateException("Proposta CANCELADA não aceita alterações."); }
    @Override
    public void voltar(Proposta proposta) { throw new IllegalStateException("Proposta CANCELADA não aceita alterações."); }
    @Override
    public void iniciarNegociacao(Proposta proposta) { throw new IllegalStateException("Proposta CANCELADA não aceita alterações."); }
    @Override
    public void enviarContraproposta(Proposta proposta) { throw new IllegalStateException("Proposta CANCELADA não aceita alterações."); }
    @Override
    public void aceitarParaNegociacao(Proposta proposta) { throw new IllegalStateException("Proposta CANCELADA não aceita alterações."); }
    @Override
    public void recusarContraproposta(Proposta proposta) { throw new IllegalStateException("Proposta CANCELADA não aceita alterações."); }
    @Override
    public void finalizarAcordo(Proposta proposta) { throw new IllegalStateException("Proposta CANCELADA não aceita alterações."); }
    @Override
    public void cancelar(Proposta proposta) { throw new IllegalStateException("A proposta já está CANCELADA."); }
}