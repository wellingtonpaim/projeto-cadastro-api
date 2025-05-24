package br.univesp.pi.domain.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ItemServicoId implements Serializable {

    private Long servicoCodigo;
    private Long produtoCodigo;

}