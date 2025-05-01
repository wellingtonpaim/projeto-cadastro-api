package br.univesp.pi.domain.dto;

import br.univesp.pi.domain.model.Endereco;
import br.univesp.pi.domain.model.Telefone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

@Data
public class EmpresaCreateDTO {

    @NotBlank
    private String razaoSocial;

    @CNPJ
    @NotBlank
    private String cnpj;

    @NotBlank
    private String inscricaoEstadual;

    private List<Telefone> telefones;

    @Email
    private String email;

    private Endereco endereco;

    private String site;

    private String logotipoPath;
}

