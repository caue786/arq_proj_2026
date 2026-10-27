package com.trokr.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "historicos")
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long propostaId;
    private String statusAnterior;
    private String statusNovo;
    private LocalDateTime dataAlteracao;
}