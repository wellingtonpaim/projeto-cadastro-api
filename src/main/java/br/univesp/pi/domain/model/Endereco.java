package br.univesp.pi.domain.model;

import br.univesp.pi.enumeration.UF;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable
public class Endereco {

    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;

    @Enumerated(EnumType.STRING)
    private UF uf;
}
