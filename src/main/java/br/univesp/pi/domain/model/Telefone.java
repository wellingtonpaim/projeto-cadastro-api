package br.univesp.pi.domain.model;

import br.univesp.pi.enumeration.TipoTelefone;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable
public class Telefone {

    @Enumerated(EnumType.STRING)
    private TipoTelefone tipo;

    private int ddd;
    private int numero;
}
