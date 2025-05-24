package br.univesp.pi.domain.dto.response;

import lombok.Data;

@Data
public class ProdutoResponseDTO {

    private Long codigo;
    private FamiliaResponseDTO familia;
    private String nome;
    private String descricao;
    private Double preco;
    private FornecedorResponseDTO fornecedor;
}
