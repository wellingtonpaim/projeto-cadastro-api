package br.univesp.pi.domain.dto.response;

import lombok.Data;

@Data
public class ItemServicoResponseDTO {
    private ProdutoResponseDTO produto;
    private Integer quantidade;
    private Double precoUnitario;
    private Double precoTotalItem;
}
