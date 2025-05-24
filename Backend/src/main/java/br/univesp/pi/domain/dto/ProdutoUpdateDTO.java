package br.univesp.pi.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoUpdateDTO {

    private Long familia;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String fornecedor;

}
