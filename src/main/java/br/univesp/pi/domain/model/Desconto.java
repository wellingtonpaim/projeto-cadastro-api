package br.univesp.pi.domain.model;

import br.univesp.pi.enumeration.TipoDesconto;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Embeddable
public class Desconto {

    @Enumerated(EnumType.STRING)
    private TipoDesconto tipo;

    private BigDecimal valor;
}
