package com.trokr.factory;

import com.trokr.dto.ItemRequestDTO;
import com.trokr.exception.RegraNegocioException;
import com.trokr.model.CategoriaItem;
import com.trokr.model.Item;
import com.trokr.model.ItemProduto;
import com.trokr.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class FabricaItemProduto implements FabricaDeItem {

    @Override
    public CategoriaItem categoriaAtendida() {
        return CategoriaItem.PRODUTO;
    }

    @Override
    public Item criar(ItemRequestDTO dados, Usuario proprietario) {
        // Validação usando os métodos do record (sem 'get')
        if (dados.pesoGramas() == null || dados.pesoGramas() <= 0) {
            throw new RegraNegocioException("ITEM_DADOS_INCOMPLETOS", "O peso em gramas é obrigatório para produtos e deve ser maior que zero.");
        }
        
        if (dados.duracaoMinutos() != null || dados.nivel() != null) {
            throw new RegraNegocioException("ITEM_CAMPO_INCOMPATIVEL", "Campos de serviço ou experiência não são permitidos em produtos.");
        }

        return ItemProduto.builder()
                .titulo(dados.titulo())
                .descricao(dados.descricao())
                .usuarioProprietario(proprietario)
                .pesoGramas(dados.pesoGramas())
                .build();
    }
}