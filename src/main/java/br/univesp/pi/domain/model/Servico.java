package br.univesp.pi.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "servico")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cliente", referencedColumnName = "cpf_ou_cnpj", nullable = false)
    private Cliente cliente;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "descricao", column = @Column(name = "mao_de_obra_descricao")),
            @AttributeOverride(name = "preco", column = @Column(name = "mao_de_obra_preco"))
    })
    private MaoDeObra maoDeObra;

    @ManyToMany
    @JoinTable(
            name = "servico_produto",
            joinColumns = @JoinColumn(name = "servico_codigo"),
            inverseJoinColumns = @JoinColumn(name = "produto_codigo")
    )
    private List<Produto> produtos;

    @Column(name = "preco_total_produtos")
    private Double precoTotalProdutos;

    @Column(name = "preco_total")
    private Double precoTotal;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "tipo", column = @Column(name = "desconto_tipo")),
            @AttributeOverride(name = "valor", column = @Column(name = "desconto_valor"))
    })
    private Desconto desconto;

    @Column(name = "preco_total_com_desconto")
    private Double precoTotalComDesconto;

    @CreationTimestamp
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

}
