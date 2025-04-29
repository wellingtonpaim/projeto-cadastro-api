package br.univesp.pi.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Embeddable
public class MaoDeObra {

    @NotBlank
    private String descricao;

    @NotNull
    private Double preco;
}
