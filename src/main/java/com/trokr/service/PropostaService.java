package com.trokr.service;

import com.trokr.dto.PropostaRequestDTO;
import com.trokr.event.TrocaConcluidaEvent;
import com.trokr.model.Item;
import com.trokr.model.Proposta;
import com.trokr.model.Status;
import com.trokr.model.Usuario;
import com.trokr.repository.ItemRepository;
import com.trokr.repository.PropostaRepository;
import com.trokr.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PropostaService {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher; // <-- INJETADO AQUI PARA O OBSERVER

    public Proposta salvar(Proposta proposta) {
        return propostaRepository.save(proposta);
    }

    public Proposta buscarPorId(Long id) {
        return propostaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proposta não encontrada com ID: " + id));
    }

    public List<Proposta> listarTodas() {
        return propostaRepository.findAll();
    }

    // --- Criação de Proposta Raiz ---
    public Proposta criar(PropostaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Item item = itemRepository.findById(dto.itemId())
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        Proposta proposta = new Proposta();
        proposta.setDescricao(dto.descricao());
        proposta.setUsuario(usuario);
        proposta.setItem(item);

        return propostaRepository.save(proposta);
    }

    // --- Criação de Contraproposta ---
    public Proposta criarContraproposta(Long propostaRaizId, PropostaRequestDTO dto) {
        Proposta raiz = buscarPorId(propostaRaizId);
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Item item = itemRepository.findById(dto.itemId())
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        Proposta contra = new Proposta();
        contra.setDescricao(dto.descricao());
        contra.setUsuario(usuario);
        contra.setItem(item);
        contra.setPropostaAnterior(raiz);
        contra.carregarEstado(); 

        return propostaRepository.save(contra);
    }

    // --- Transições da Máquina Raiz ---
    public void solicitarHomologacao(Long id) {
        Proposta p = buscarPorId(id);
        p.solicitarHomologacao();
        propostaRepository.save(p);
    }

    public void aprovarHomologacao(Long id) {
        Proposta p = buscarPorId(id);
        p.aprovarHomologacao();
        propostaRepository.save(p);
    }

    public void recusarHomologacao(Long id) {
        Proposta p = buscarPorId(id);
        p.recusarHomologacao();
        propostaRepository.save(p);
    }

    public void iniciarNegociacao(Long id) {
        Proposta p = buscarPorId(id);
        p.iniciarNegociacao();
        propostaRepository.save(p);
    }

    // --- Transições da Contraproposta ---
    public void enviarContraproposta(Long id) {
        Proposta p = buscarPorId(id);
        p.enviarContraproposta();
        propostaRepository.save(p);
    }

    public void aceitarParaNegociacao(Long id) {
        Proposta contra = buscarPorId(id);
        contra.aceitarParaNegociacao(); 
        propostaRepository.save(contra);
        propostaRepository.save(contra.getPropostaAnterior());
    }

    public void recusarContraproposta(Long id) {
        Proposta p = buscarPorId(id);
        p.recusarContraproposta();
        propostaRepository.save(p);
    }

    // --- Transições Gerais ---
    public void cancelar(Long id) {
        Proposta p = buscarPorId(id);
        p.cancelar();
        propostaRepository.save(p);
    }

    // --- ATUALIZADO PARA DISPARAR O EVENTO DA AULA 7 ---
    public void finalizarAcordo(Long id) {
        Proposta p = buscarPorId(id);
        p.finalizarAcordo();
        
        // Se a transição levou efetivamente ao estado FINALIZADO, publicamos o evento
        if (p.getStatus() == Status.FINALIZADO) {
            TrocaConcluidaEvent evento = construirEventoDeTrocaConcluida(p);
            if (evento != null) {
                eventPublisher.publishEvent(evento); // Dispara o Observer!
            }
        }
        
        propostaRepository.save(p);
    }

    // Método auxiliar que resolve os dois cenários (Raiz ou Contraproposta finalizando)
    private TrocaConcluidaEvent construirEventoDeTrocaConcluida(Proposta proposta) {
        Usuario usuarioA;
        Usuario usuarioB;
        Item itemA;
        Item itemB;
        Long propostaRaizId;

        if (proposta.isContraproposta()) {
            // Cenário B: A Contraproposta está a finalizar
            Proposta raiz = proposta.getPropostaAnterior();
            propostaRaizId = raiz.getId();
            
            usuarioA = raiz.getUsuario();
            itemA = raiz.getItem();
            
            usuarioB = proposta.getUsuario();
            itemB = proposta.getItem();
        } else {
            // Cenário A: A Proposta Raiz está a finalizar
            propostaRaizId = proposta.getId();
            usuarioA = proposta.getUsuario();
            itemA = proposta.getItem();
            
            // Localiza a contraproposta que estava associada e finalizada/negociada
            Proposta contraAssociada = proposta.getContrapropostas().stream()
                .filter(c -> c.getStatus() == Status.FINALIZADO || c.getStatus() == Status.NEGOCIADO)
                .findFirst()
                .orElse(null);
            
            if (contraAssociada == null) return null;
            
            usuarioB = contraAssociada.getUsuario();
            itemB = contraAssociada.getItem();
        }

        return new TrocaConcluidaEvent(
            propostaRaizId,
            usuarioA,
            usuarioB,
            itemA,
            itemB,
            LocalDateTime.now()
        );
    }
}