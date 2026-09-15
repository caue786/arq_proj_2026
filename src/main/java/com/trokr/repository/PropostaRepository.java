package com.trokr.repository;

import com.trokr.model.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    
    // Essencial para o efeito cascata de EstadoNegociado (Aula 05)
    List<Proposta> findByPropostaAnteriorId(Long id);
}