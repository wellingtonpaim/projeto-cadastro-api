package br.univesp.pi.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ValidacaoCamposResponse {

    private LocalDateTime timestamp;
    private int status;
    private String entidade;
    private String mensagem;
    private List<String> erros;
}

