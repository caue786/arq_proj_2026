package com.trokr.factory;

import com.trokr.dto.ItemRequestDTO;
import com.trokr.exception.RegraNegocioException;
import com.trokr.model.CategoriaItem;
import com.trokr.model.Item;
import com.trokr.model.ItemExperiencia;
import com.trokr.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class FabricaItemExperiencia implements FabricaDeItem {

    @Override
    public CategoriaItem categoriaAtendida() {
        return CategoriaItem.EXPERIENCIA;
    }

    @Override
    public Item criar(ItemRequestDTO dados, Usuario proprietario) {
        if (dados.nivel() == null) {
            throw new RegraNegocioException("ITEM_DADOS_INCOMPLETOS", "O nível é obrigatório para experiências.");
        }
        
        if (dados.pesoGramas() != null || dados.duracaoMinutos() != null) {
            throw new RegraNegocioException("ITEM_CAMPO_INCOMPATIVEL", "Campos de produto ou serviço não são permitidos em experiências.");
        }

        return ItemExperiencia.builder()
                .titulo(dados.titulo())
                .descricao(dados.descricao())
                .usuarioProprietario(proprietario)
                .nivel(dados.nivel())
                .build();
    }
}