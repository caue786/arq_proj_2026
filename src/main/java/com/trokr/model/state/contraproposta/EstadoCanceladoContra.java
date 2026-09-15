package com.trokr.model.state.contraproposta;

import com.trokr.model.Proposta;
import com.trokr.model.state.proposta.EstadoProposta;

public class EstadoCanceladoContra implements EstadoProposta {

    @Override
    public void solicitarHomologacao(Proposta proposta) { throw new IllegalStateException("Esta contraproposta foi cancelada e não aceita alterações."); }
    
    @Override
    public void aprovarHomologacao(Proposta proposta) { throw new IllegalStateException("Esta contraproposta foi cancelada e não aceita alterações."); }
    
    @Override
    public void recusarHomologacao(Proposta proposta) { throw new IllegalStateException("Esta contraproposta foi cancelada e não aceita alterações."); }
    
    @Override
    public void voltar(Proposta proposta) { throw new IllegalStateException("Esta contraproposta foi cancelada e não aceita alterações."); }
    
    @Override
    public void iniciarNegociacao(Proposta proposta) { throw new IllegalStateException("Esta contraproposta foi cancelada e não aceita alterações."); }
    
    @Override
    public void enviarContraproposta(Proposta proposta) { throw new IllegalStateException("Esta contraproposta foi cancelada e não aceita alterações."); }
    
    @Override
    public void aceitarParaNegociacao(Proposta proposta) { throw new IllegalStateException("Esta contraproposta foi cancelada e não aceita alterações."); }
    
    @Override
    public void recusarContraproposta(Proposta proposta) { throw new IllegalStateException("Esta contraproposta foi cancelada e não aceita alterações."); }
    
    @Override
    public void finalizarAcordo(Proposta proposta) { throw new IllegalStateException("Esta contraproposta foi cancelada e não aceita alterações."); }
    
    @Override
    public void cancelar(Proposta proposta) { throw new IllegalStateException("A contraproposta já está CANCELADA."); }
}