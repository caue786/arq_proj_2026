package com.trokr.service;

import com.trokr.dto.PropostaRequestDTO;
import com.trokr.model.Item;
import com.trokr.model.Proposta;
import com.trokr.model.Usuario;
import com.trokr.repository.ItemRepository;
import com.trokr.repository.PropostaRepository;
import com.trokr.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropostaService {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ItemRepository itemRepository;

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
        // Garante que nasce no estado de rascunho de contraproposta
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
        // Dispara o Domain Model rico (muda contra, raiz e recusa concorrentes)
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

    public void finalizarAcordo(Long id) {
        Proposta p = buscarPorId(id);
        p.finalizarAcordo();
        propostaRepository.save(p);
    }
}