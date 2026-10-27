package com.trokr.model;

import com.trokr.dto.ItemRequestDTO;
import com.trokr.exception.RegraNegocioException;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("EXPERIENCIA")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemExperiencia extends Item {

    @Enumerated(EnumType.STRING)
    private NivelExperiencia nivel;

    @Override
    public CategoriaItem getCategoria() {
        return CategoriaItem.EXPERIENCIA;
    }

    @Override
    public void atualizar(ItemRequestDTO dados) {
        if (dados.nivel() == null) {
            throw new RegraNegocioException("ITEM_DADOS_INCOMPLETOS", "O nível é obrigatório para experiências.");
        }
        setTitulo(dados.titulo());
        setDescricao(dados.descricao());
        this.nivel = dados.nivel();
    }
}