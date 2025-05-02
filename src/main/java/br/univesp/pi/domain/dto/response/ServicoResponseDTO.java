package br.univesp.pi.domain.dto.response;

import br.univesp.pi.domain.model.Desconto;
import br.univesp.pi.domain.model.MaoDeObra;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ServicoResponseDTO {

    private Long codigo;
    private ClienteResponseDTO cliente;
    private MaoDeObra maoDeObra;
    private List<ProdutoResponseDTO> produtos;
    private Double precoTotalProdutos;
    private Double precoTotal;
    private Desconto desconto;
    private Double precoTotalComDesconto;
    private LocalDateTime dataCriacao;
}
