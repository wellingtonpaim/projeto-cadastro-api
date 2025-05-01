package br.univesp.pi.domain.dto;

import br.univesp.pi.domain.model.Desconto;
import br.univesp.pi.domain.model.MaoDeObra;
import br.univesp.pi.validation.CpfCnpj;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

@Data
public class ServicoCreateDTO {

    @NotBlank
    @CpfCnpj
    private String cliente; // CPF ou CNPJ

    private MaoDeObra maoDeObra;

    private List<Long> produtos; // Lista de códigos dos produtos

    private Desconto desconto;
}

