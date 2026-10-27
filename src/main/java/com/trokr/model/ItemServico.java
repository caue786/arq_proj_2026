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
@DiscriminatorValue("SERVICO")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemServico extends Item {

    private Integer duracaoMinutos;

    @Override
    public CategoriaItem getCategoria() {
        return CategoriaItem.SERVICO;
    }

    @Override
    public void atualizar(ItemRequestDTO dados) {
        if (dados.duracaoMinutos() == null || dados.duracaoMinutos() <= 0) {
            throw new RegraNegocioException("ITEM_DADOS_INCOMPLETOS",
                    "A duração em minutos é obrigatória para serviços e deve ser maior que zero.");
        }
        setTitulo(dados.titulo());
        setDescricao(dados.descricao());
        this.duracaoMinutos = dados.duracaoMinutos();
    }

}
