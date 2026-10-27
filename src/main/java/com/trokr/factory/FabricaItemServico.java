package com.trokr.factory;

import com.trokr.dto.ItemRequestDTO;
import com.trokr.exception.RegraNegocioException;
import com.trokr.model.CategoriaItem;
import com.trokr.model.Item;
import com.trokr.model.ItemServico;
import com.trokr.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class FabricaItemServico implements FabricaDeItem {

    @Override
    public CategoriaItem categoriaAtendida() {
        return CategoriaItem.SERVICO;
    }

    @Override
    public Item criar(ItemRequestDTO dados, Usuario proprietario) {
        if (dados.duracaoMinutos() == null || dados.duracaoMinutos() <= 0) {
            throw new RegraNegocioException("ITEM_DADOS_INCOMPLETOS", "A duração em minutos é obrigatória para serviços e deve ser maior que zero.");
        }
        
        if (dados.pesoGramas() != null || dados.nivel() != null) {
            throw new RegraNegocioException("ITEM_CAMPO_INCOMPATIVEL", "Campos de produto ou experiência não são permitidos em serviços.");
        }

        return ItemServico.builder()
                .titulo(dados.titulo())
                .descricao(dados.descricao())
                .usuarioProprietario(proprietario)
                .duracaoMinutos(dados.duracaoMinutos())
                .build();
    }
}