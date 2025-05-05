package br.univesp.pi.domain.dto;

import br.univesp.pi.domain.model.Endereco;
import br.univesp.pi.domain.model.Telefone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

@Data
public class EmpresaUpdateDTO {

    private String razaoSocial;
    private String cnpj;
    private String nomeFantasia;
    private String inscricaoEstadual;
    private List<Telefone> telefones;
    private String email;
    private Endereco endereco;
    private String site;
    private String logotipoPath;
}

