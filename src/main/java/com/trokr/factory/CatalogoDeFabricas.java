package com.trokr.factory;

import com.trokr.dto.ItemRequestDTO;
import com.trokr.exception.RegraNegocioException;
import com.trokr.model.CategoriaItem;
import com.trokr.model.Item;
import com.trokr.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CatalogoDeFabricas {

    private final Map<CategoriaItem, FabricaDeItem> fabricas;

    public CatalogoDeFabricas(List<FabricaDeItem> listaDeFabricas) {
        this.fabricas = listaDeFabricas.stream()
                .collect(Collectors.toMap(
                        FabricaDeItem::categoriaAtendida,
                        fabrica -> fabrica
                ));
    }

    public Item criar(ItemRequestDTO dados, Usuario proprietario) {
        FabricaDeItem fabrica = fabricas.get(dados.categoria());
        if (fabrica == null) {
            throw new RegraNegocioException("ITEM_CATEGORIA_INVALIDA", "Categoria de item desconhecida ou não suportada.");
        }
        return fabrica.criar(dados, proprietario);
    }
}