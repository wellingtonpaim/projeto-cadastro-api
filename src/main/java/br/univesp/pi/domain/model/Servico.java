package br.univesp.pi.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @ManyToOne
    private Usuario cliente;

    @Embedded
    private MaoDeObra maoDeObra;

    @ManyToMany
    private List<Produto> produtos;

    private Double precoTotal;

    @Embedded
    private Desconto desconto;

    private Double precoTotalComDesconto;
}
