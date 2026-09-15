package com.trokr.model;

import com.trokr.model.state.*;
import com.trokr.model.state.proposta.*;
// Deixe os imports de contraproposta comentados se ainda não preencheu as classes:
// import com.trokr.model.state.contraproposta.*; 

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "propostas")
@Getter
@Setter
@AllArgsConstructor
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proposta_anterior_id")
    private Proposta propostaAnterior;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item; 

    @OneToMany(mappedBy = "propostaAnterior", cascade = CascadeType.ALL)
    private List<Proposta> contrapropostas = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Transient
    private EstadoProposta estadoAtual;

    public Proposta() {
        this.status = Status.RASCUNHO;
        this.estadoAtual = new EstadoRascunho();
    }

    public boolean isContraproposta() {
        return this.propostaAnterior != null;
    }

    @PostLoad
    public void carregarEstado() {
        if (this.status == null) return;
        
        switch (this.status) {
            case RASCUNHO -> this.estadoAtual = isContraproposta() ? new com.trokr.model.state.contraproposta.EstadoRascunhoContra() : new EstadoRascunho();
            case HOMOLOGACAO -> this.estadoAtual = new EstadoHomologacao();
            case ATIVA -> this.estadoAtual = new EstadoAtiva();
            case NEGOCIADO -> this.estadoAtual = isContraproposta() ? new com.trokr.model.state.contraproposta.EstadoNegociadoContra() : new EstadoNegociado();
            case FINALIZADO -> this.estadoAtual = isContraproposta() ? new com.trokr.model.state.contraproposta.EstadoFinalizadoContra() : new EstadoFinalizado();
            case CANCELADO -> this.estadoAtual = isContraproposta() ? new com.trokr.model.state.contraproposta.EstadoCanceladoContra() : new EstadoCancelado();
            case EM_ANALISE -> this.estadoAtual = new com.trokr.model.state.contraproposta.EstadoEm_Analise();
            case RECUSADO -> this.estadoAtual = new com.trokr.model.state.contraproposta.EstadoRecusado();
        }
    }

    // Método principal para mudar de estado (atualiza a classe e o banco juntos)
    public void mudarEstadoPara(EstadoProposta novoEstado, Status novoStatus) {
        this.estadoAtual = novoEstado;
        this.status = novoStatus;
    }

    // --- DELEGAÇÃO (State Pattern) ---
    public void solicitarHomologacao() { this.estadoAtual.solicitarHomologacao(this); }
    public void aprovarHomologacao() { this.estadoAtual.aprovarHomologacao(this); }
    public void recusarHomologacao() { this.estadoAtual.recusarHomologacao(this); }
    public void voltar() { this.estadoAtual.voltar(this); }
    public void iniciarNegociacao() { this.estadoAtual.iniciarNegociacao(this); }
    public void enviarContraproposta() { this.estadoAtual.enviarContraproposta(this); }
    public void aceitarParaNegociacao() { this.estadoAtual.aceitarParaNegociacao(this); }
    public void recusarContraproposta() { this.estadoAtual.recusarContraproposta(this); }
    public void finalizarAcordo() { this.estadoAtual.finalizarAcordo(this); }
    public void cancelar() { this.estadoAtual.cancelar(this); }

    // --- Lógica Extra do Domínio Rico (Efeito Cascata) ---
    public void recusarDemaisContrapropostas(Proposta contraAceita) {
        for (Proposta cp : this.contrapropostas) {
            if (!cp.getId().equals(contraAceita.getId()) && cp.getStatus() != Status.RECUSADO && cp.getStatus() != Status.CANCELADO) {
                cp.recusarContraproposta();
            }
        }
    }
}