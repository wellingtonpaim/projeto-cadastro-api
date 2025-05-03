package br.univesp.pi.exception.handler;

import br.univesp.pi.domain.dto.response.ApiResponse;
import br.univesp.pi.exception.ApiIllegalArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidacaoCampos(MethodArgumentNotValidException ex) {
        String nomeEntidade = "entidade desconhecida";
        if (ex.getBindingResult().getTarget() != null) {
            nomeEntidade = ex.getBindingResult().getTarget().getClass().getSimpleName().replace("DTO", "");
        }

        List<String> mensagensErro = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> "Campo '" + erro.getField() + "' " + erro.getDefaultMessage())
                .collect(Collectors.toList());

        String mensagem = "Todos os campos de " + nomeEntidade + " são obrigatórios. Verifique e tente novamente.";

        ApiResponse<Void> resposta = new ApiResponse<>(
                false,
                mensagem,
                null,
                mensagensErro,
                new Date()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        String mensagem = ex.getMessage();
        List<String> erros = List.of("Erro de argumento: " + mensagem);
        ApiResponse<Void> resposta = new ApiResponse<>(
                false,
                "Erro ao processar a requisição.",
                null,
                erros,
                new Date()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    @ExceptionHandler(ApiIllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleApiIllegalArgument(ApiIllegalArgumentException ex) {
        String suggestion = determineSuggestion(ex.getEntity(), ex.getField(), ex.getValue());
        List<String> erros = List.of(
                ex.getMessage(),
                "Campo: " + ex.getField(),
                "Valor: " + ex.getValue(),
                "Sugestão: " + suggestion
        );

        ApiResponse<Void> resposta = new ApiResponse<>(
                false,
                "Erro relacionado à entidade " + ex.getEntity(),
                null,
                erros,
                new Date()
        );

        return ResponseEntity.status(ex.getStatus()).body(resposta);
    }

    private String determineSuggestion(String entity, String field, Object value) {
        if (entity.equalsIgnoreCase("Fornecedor") && field.equalsIgnoreCase("CPF/CNPJ")) {
            return "Verifique se o fornecedor com " + field + " " + value + " está cadastrado no sistema.";
        } else if (entity.equalsIgnoreCase("Produto")) {
            return "Verifique os dados do produto e certifique-se de que todos os relacionamentos estão corretos.";
        }
        return "Verifique os dados fornecidos e tente novamente.";
    }
}
