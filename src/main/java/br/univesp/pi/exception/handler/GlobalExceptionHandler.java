package br.univesp.pi.exception.handler;

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
}
