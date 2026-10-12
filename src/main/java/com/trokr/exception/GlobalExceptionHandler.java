package com.trokr.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Tratamento de exceção MUITO simples de propósito nesta fase do curso:
 * só um handler genérico para "não encontrado". Erros de validação
 * (@Valid) já viram 400 automaticamente pelo Spring, sem código extra.
 * Um tratamento de erros padronizado e mais completo é conteúdo de uma
 * aula futura.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("erro", ex.getMessage()));
    }
    // --- NOVO HANDLER PARA A CORRIDA 1 (Lock Otimista) ---
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<Map<String, String>> handleConflito(ObjectOptimisticLockingFailureException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "erro", "Conflito de Concorrência",
                        "mensagem", "Esta proposta foi alterada por outro utilizador ao mesmo tempo. Por favor, recarregue e tente novamente."
                ));
    }
}
