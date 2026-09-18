package com.trokr.event;

import com.trokr.model.Item;
import com.trokr.model.Usuario;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TrocaConcluidaEvent {
    private final Long propostaId;
    private final Usuario usuarioA;
    private final Usuario usuarioB;
    private final Item itemA;
    private final Item itemB;
    private final LocalDateTime dataConclusao;
}