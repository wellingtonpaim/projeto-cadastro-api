package br.univesp.pi.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "servico_produto")
public class ItemServico {

    @EmbeddedId
    private ItemServicoId id = new ItemServicoId();

    @ManyToOne
    @MapsId("servicoCodigo")
    @JoinColumn(name = "servico_codigo")
    private Servico servico;

    @ManyToOne
    @MapsId("produtoCodigo")
    @JoinColumn(name = "produto_codigo")
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario", nullable = false)
    private Double precoUnitario;

    @Column(name = "preco_total_item")
    private Double precoTotalItem;

    public void setServico(Servico servico) {
        this.servico = servico;
        this.id.setServicoCodigo(servico.getCodigo());
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
        this.id.setProdutoCodigo(produto.getCodigo());
    }
}