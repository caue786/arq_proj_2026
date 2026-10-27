package com.trokr.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Estratégia de tabela única para a hierarquia[cite: 10, 11]
@DiscriminatorColumn(name = "categoria", discriminatorType = DiscriminatorType.STRING) // A coluna categoria vira o
                                                                                       // discriminador[cite: 11, 13]
@Getter
@Setter
@SuperBuilder // Necessário para permitir a construção de objetos com herança
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Exigido pelo JPA para entidades
@AllArgsConstructor(access = AccessLevel.PRIVATE) // Utilizado pelo @SuperBuilder[cite: 25]
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    // O tipo do objeto define a categoria. Cada subclasse implementará o seu
    // próprio retorno
    public abstract CategoriaItem getCategoria();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuarioProprietario;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    public abstract void atualizar(com.trokr.dto.ItemRequestDTO dados);
}