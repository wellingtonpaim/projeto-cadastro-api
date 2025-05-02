package br.univesp.pi.exception.handler;

import br.univesp.pi.exception.ApiIllegalArgumentException;
import br.univesp.pi.exception.ErrorResponse;
import br.univesp.pi.exception.ValidacaoCamposResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidacaoCamposResponse> handleValidacaoCampos(MethodArgumentNotValidException ex) {

        String nomeEntidade = "entidade desconhecida";
        if (ex.getBindingResult().getTarget() != null) {
            nomeEntidade = ex.getBindingResult().getTarget().getClass().getSimpleName();
            // Remove "DTO" do nome se existir
            nomeEntidade = nomeEntidade.replace("DTO", "");
        }

        List<String> mensagensErro = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> "Campo '" + erro.getField() + "' " + erro.getDefaultMessage())
                .collect(Collectors.toList());

        ValidacaoCamposResponse resposta = ValidacaoCamposResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .entidade(nomeEntidade)
                .mensagem("Todos os campos de " + nomeEntidade + " são obrigatórios, verifique e tente novamente.")
                .erros(mensagensErro)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        String entity = "Entidade";
        String field = "campo";
        Object value = "valor";
        String suggestion = "Verifique os dados fornecidos e tente novamente.";

        String message = ex.getMessage();
        if (message != null && message.contains("não encontrado")) {
            String[] parts = message.split(":");
            if (parts.length > 1) {
                value = parts[1].trim();
            }
        }

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                entity,
                ex.getMessage(),
                suggestion
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(ApiIllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleApiIllegalArgument(ApiIllegalArgumentException ex) {
        String entity = ex.getEntity();
        String field = ex.getField();
        Object value = ex.getValue();
        String suggestion = determineSuggestion(entity, field, value);

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                entity,
                ex.getMessage(),
                suggestion
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    private String determineSuggestion(String entity, String field, Object value) {
        // Lógica para gerar sugestões com base na entidade/campo
        if (entity.equalsIgnoreCase("Fornecedor") && field.equalsIgnoreCase("CPF/CNPJ")) {
            return "Verifique se o fornecedor com " + field + " " + value + " está cadastrado no sistema.";
        } else if (entity.equalsIgnoreCase("Produto")) {
            return "Verifique os dados do produto e certifique-se de que todos os relacionamentos estão corretos.";
        }
        return "Verifique os dados fornecidos e tente novamente.";
    }
}
