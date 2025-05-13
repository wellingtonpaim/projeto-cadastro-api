package br.univesp.pi.domain.dto;

import br.univesp.pi.domain.model.Desconto;
import br.univesp.pi.domain.model.MaoDeObra;
import br.univesp.pi.validation.CpfCnpj;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class ServicoCreateDTO {

    @NotBlank
    @CpfCnpj
    private String cliente; // CPF ou CNPJ

    private MaoDeObra maoDeObra;
    private Set<ServicoItemDTO> itens;
    private Desconto desconto;
}

