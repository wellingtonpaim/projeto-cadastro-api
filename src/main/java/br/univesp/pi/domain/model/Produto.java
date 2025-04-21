package br.univesp.pi.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @ManyToOne
    private Familia familia;

    private String nome;
    private String descricao;
    private Double preco;
}
