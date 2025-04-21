package br.univesp.pi.domain.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class MaoDeObra {

    private String descricao;
    private Double preco;
}
