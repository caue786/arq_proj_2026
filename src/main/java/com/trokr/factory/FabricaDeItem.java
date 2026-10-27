package com.trokr.factory;

import com.trokr.dto.ItemRequestDTO; // Ajusta o import conforme o pacote do teu DTO
import com.trokr.model.CategoriaItem;
import com.trokr.model.Item;
import com.trokr.model.Usuario;

public interface FabricaDeItem {
    CategoriaItem categoriaAtendida();
    Item criar(ItemRequestDTO dados, Usuario proprietario);
}