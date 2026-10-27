package com.trokr.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// @RestControllerAdvice centraliza o tratamento de exceções para todos os controladores REST da aplicação
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Método auxiliar privado para padronizar a criação de todos os ProblemDetail (RFC 9457).
     * Garante que todas as respostas de erro mantêm a mesma estrutura previsível e estável.
     */
    private ProblemDetail criarProblemDetail(HttpStatus status, String titulo, String codigo, String detalhe, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, detalhe);
        pd.setTitle(titulo);
        pd.setType(URI.create("https://trokr.com/erros/" + codigo.toLowerCase()));
        pd.setInstance(URI.create(request.getRequestURI()));
        pd.setProperty("codigo", codigo); // Código estável do contrato (ex: PROPOSTA_NAO_ENCONTRADA)[cite: 14, 16]
        pd.setProperty("timestamp", Instant.now()); // Registo temporal exato da ocorrência[cite: 14]
        return pd;
    }

    // Trata recursos não encontrados (IDs inexistentes) -> Mapeado para o Status 404[cite: 12, 25]
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleRecursoNaoEncontrado(ResourceNotFoundException ex, HttpServletRequest request) {
        return criarProblemDetail(HttpStatus.NOT_FOUND, "Recurso Não Encontrado", ex.getCodigo(), ex.getMessage(), request);
    }

    // Trata transições de estado inválidas no domínio -> Mapeado para o Status 409[cite: 12, 25]
    @ExceptionHandler(TransicaoInvalidaException.class)
    public ProblemDetail handleTransicaoInvalida(TransicaoInvalidaException ex, HttpServletRequest request) {
        return criarProblemDetail(HttpStatus.CONFLICT, "Transição Inválida", ex.getCodigo(), ex.getMessage(), request);
    }

    // Trata violações gerais de regras de negócio (dados válidos, mas regra proíbe) -> Mapeado para o Status 422[cite: 12, 25]
    @ExceptionHandler(RegraNegocioException.class)
    public ProblemDetail handleRegraNegocio(RegraNegocioException ex, HttpServletRequest request) {
        return criarProblemDetail(HttpStatus.UNPROCESSABLE_ENTITY, "Violação de Regra de Negócio", ex.getCodigo(), ex.getMessage(), request);
    }

    // Trata conflitos de concorrência gerados pelo bloqueio otimista (@Version - Aula 10) -> Mapeado para o Status 409[cite: 12, 24, 27]
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ProblemDetail handleOptimisticLock(OptimisticLockingFailureException ex, HttpServletRequest request) {
        return criarProblemDetail(HttpStatus.CONFLICT, "Conflito de Concorrência", "PROPOSTA_ALTERADA_CONCORRENTEMENTE", "Esta proposta foi alterada por outro utilizador. Atualize e tente novamente.", request);
    }

    /**
     * Sobrescreve o método do Spring para lidar com falhas de validação de dados (@Valid).
     * Devolve um Status 400 com a lista detalhada de campos inválidos para o front-end[cite: 16, 25].
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        ProblemDetail pd = criarProblemDetail(HttpStatus.BAD_REQUEST, "Dados Inválidos", "VALIDACAO", "A validação dos dados da requisição falhou.", servletRequest);

        // Mapeia cada erro de campo para permitir que a interface destaque o input incorreto[cite: 16]
        List<FieldErrorDto> campos = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new FieldErrorDto(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        pd.setProperty("campos", campos);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    // DTO auxiliar privado para estruturar o array de campos inválidos na resposta de validação[cite: 16]
    public record FieldErrorDto(String campo, String mensagem) {}

    /**
     * Catch-All: Trata qualquer excepção não prevista (erros inesperados) -> Mapeado para o Status 500[cite: 24, 28].
     * Garante a segurança ao ocultar detalhes internos (como SQL ou stack traces) do cliente[cite: 28].
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleErroInesperado(Exception ex, HttpServletRequest request) {
        // Gera um ID de ocorrência único para cruzar a resposta do utilizador com o log do servidor[cite: 28]
        String ocorrencia = UUID.randomUUID().toString();
        logger.error("Erro inesperado [Ocorrência ID: " + ocorrencia + "]", ex);

        ProblemDetail pd = criarProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Erro Interno do Servidor", "ERRO_INTERNO", "Ocorreu um erro inesperado no servidor.", request);
        pd.setProperty("ocorrencia", ocorrencia); // O elo entre o cliente e o suporte técnico[cite: 28]
        return pd;
    }
}