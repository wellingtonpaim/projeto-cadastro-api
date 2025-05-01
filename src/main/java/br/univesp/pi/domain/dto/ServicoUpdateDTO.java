package br.univesp.pi.domain.dto;

import br.univesp.pi.domain.model.Desconto;
import br.univesp.pi.domain.model.MaoDeObra;
import br.univesp.pi.validation.CpfCnpj;
import lombok.Data;

import java.util.List;

@Data
public class ServicoUpdateDTO {

    @CpfCnpj
    private String cliente; // CPF ou CNPJ

    private MaoDeObra maoDeObra;
    private List<Long> produtos; // Lista de códigos dos produtos
    private Desconto desconto;
}

