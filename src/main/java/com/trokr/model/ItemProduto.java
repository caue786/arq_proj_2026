package com.trokr.model;

import com.trokr.dto.ItemRequestDTO;
import com.trokr.exception.RegraNegocioException;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("PRODUTO")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemProduto extends Item {

    private Integer pesoGramas;

    @Override
    public CategoriaItem getCategoria() {
        return CategoriaItem.PRODUTO;
    }

    @Override
    public void atualizar(ItemRequestDTO dados) {
        if (dados.pesoGramas() == null || dados.pesoGramas() <= 0) {
            throw new RegraNegocioException("ITEM_DADOS_INCOMPLETOS",
                    "O peso em gramas é obrigatório para produtos e deve ser maior que zero.");
        }
        setTitulo(dados.titulo());
        setDescricao(dados.descricao());
        this.pesoGramas = dados.pesoGramas();
    }
}