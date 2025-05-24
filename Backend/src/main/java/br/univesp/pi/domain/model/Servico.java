package br.univesp.pi.domain.model;

import br.univesp.pi.enumeration.TipoDesconto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "servico")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "cliente", referencedColumnName = "cpf_ou_cnpj", nullable = false)
    private Cliente cliente;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "descricao", column = @Column(name = "mao_de_obra_descricao")),
            @AttributeOverride(name = "preco", column = @Column(name = "mao_de_obra_preco"))
    })
    private MaoDeObra maoDeObra;

    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemServico> itens = new ArrayList<>();

    @Column(name = "preco_total_produtos")
    private BigDecimal precoTotalProdutos;

    @Column(name = "preco_total")
    private BigDecimal precoTotal;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "tipo", column = @Column(name = "desconto_tipo")),
            @AttributeOverride(name = "valor", column = @Column(name = "desconto_valor"))
    })
    private Desconto desconto;

    @Column(name = "preco_total_com_desconto")
    private BigDecimal precoTotalComDesconto;

    @CreationTimestamp
    @Column(name = "data_criacao")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "America/Sao_Paulo")
    private LocalDateTime dataCriacao;

    public void adicionarItem(Produto produto, Integer quantidade) {
        if (this.itens == null) {
            this.itens = new ArrayList<>();
        }

        ItemServico item = new ItemServico();
        item.setServico(this);
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(produto.getPreco());
        item.setPrecoTotalItem(produto.getPreco().multiply(BigDecimal.valueOf(quantidade)));

        this.itens.add(item);
    }

    public void calcularTotais() {
        this.precoTotalProdutos = itens.stream()
                .map(ItemServico::getPrecoTotalItem)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal precoMaoDeObra = this.maoDeObra != null ? this.maoDeObra.getPreco() : BigDecimal.ZERO;
        this.precoTotal = this.precoTotalProdutos.add(precoMaoDeObra);

        this.precoTotalComDesconto = calcularPrecoComDesconto(this.precoTotal, this.desconto);

    }

    private BigDecimal calcularPrecoComDesconto(BigDecimal precoTotal, Desconto desconto) {
        if (desconto == null || desconto.getValor() == null) {
            return precoTotal;
        }

        BigDecimal valorDesconto = desconto.getTipo() == TipoDesconto.PORCENTAGEM
                ? (desconto.getValor().divide(BigDecimal.valueOf(100.0)).multiply(precoTotal))
                : desconto.getValor();

        return precoTotal.subtract(valorDesconto);
    }
}