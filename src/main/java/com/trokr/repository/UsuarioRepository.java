package com.trokr.repository;
import java.util.List;
import java.util.Optional;
import com.trokr.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
// Uso direto do Spring Data JPA, sem interface/abstração genérica de
// repositório por cima — não há necessidade disso ainda.
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByNomeContaining(String trecho);
    List<Usuario> findTop5ByOrderByDataCriacaoDesc();
    boolean existsByEmail(String Email);
// --- NOVO MÉTODO PARA A CORRIDA 2 (UPDATE Atômico) ---
    // O banco soma sozinho e previne o lost update (atualização perdida)
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Usuario u SET u.saldoCreditos = u.saldoCreditos + :valor WHERE u.id = :id")
    int adicionarCreditos(@Param("id") Long id, @Param("valor") int valor);
}
