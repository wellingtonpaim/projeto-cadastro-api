package br.univesp.pi.domain.dto;

import lombok.Data;

@Data
public class ProdutoDTO {

    private Long familia;
    private String nome;
    private String descricao;
    private Double preco;
    private String fornecedor;

}
