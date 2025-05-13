package br.univesp.pi.domain.dto;

import br.univesp.pi.domain.model.Endereco;
import br.univesp.pi.domain.model.Telefone;
import lombok.Data;

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

