package com.trokr.service;

import com.trokr.dto.ItemRequestDTO;
import com.trokr.exception.RegraNegocioException;
import com.trokr.exception.ResourceNotFoundException;
import com.trokr.factory.CatalogoDeFabricas;
import com.trokr.model.Item;
import com.trokr.model.Usuario;
import com.trokr.repository.ItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UsuarioService usuarioService;
    private final CatalogoDeFabricas catalogoDeFabricas;

    public List<Item> listarTodos() {
        return itemRepository.findAll();
    }

    public Item buscarPorId(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com id " + id));
    }

    public Item criar(ItemRequestDTO dados, Long usuarioId) {
        Usuario dono = usuarioService.buscarPorId(usuarioId);
        Item item = catalogoDeFabricas.criar(dados, dono);
        return itemRepository.save(item);
    }

    public Item atualizar(Long id, ItemRequestDTO dados) {
        Item itemExistente = buscarPorId(id);

        // Valida se a categoria é imutável[cite: 20]
        if (itemExistente.getCategoria() != dados.categoria()) {
            throw new RegraNegocioException("ITEM_CATEGORIA_IMUTAVEL", "A categoria do item não pode ser alterada.");
        }

        // Delega a atualização para a subclasse de forma polimórfica (sem instanceof no
        // service!)
        itemExistente.atualizar(dados);

        return itemRepository.save(itemExistente);
    }

    public void remover(Long id) {
        Item item = buscarPorId(id);
        itemRepository.delete(item);
    }

    public Item buscarPorTitulo(String titulo) {
        return itemRepository.findByTitulo(titulo)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com título " + titulo));
    }

    public Item buscarPorDescricao(String descricao) {
        return itemRepository.findByDescricao(descricao)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com descrição " + descricao));
    }

    public List<Item> buscarPorTituloContaining(String titulo) {
        return itemRepository.findByTituloContaining(titulo);
    }

    public List<Item> buscarPorDescricaoContaining(String descricao) {
        return itemRepository.findByDescricaoContaining(descricao);
    }

    public List<Item> listarPorUsuario(Long usuarioId) {
        return itemRepository.findByUsuarioProprietarioId(usuarioId);
    }
}