package br.univesp.pi.domain.dto.response;

import br.univesp.pi.domain.model.Desconto;
import br.univesp.pi.domain.model.MaoDeObra;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ServicoResponseDTO {

    private Long codigo;
    private ClienteResponseDTO cliente;
    private MaoDeObra maoDeObra;
    private List<ItemServicoResponseDTO> itens;
    private Double precoTotalProdutos;
    private Double precoTotal;
    private Desconto desconto;
    private Double precoTotalComDesconto;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "America/Sao_Paulo")
    private LocalDateTime dataCriacao;
}
