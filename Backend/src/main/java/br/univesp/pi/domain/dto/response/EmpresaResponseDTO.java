package br.univesp.pi.domain.dto.response;

import br.univesp.pi.domain.model.Endereco;
import br.univesp.pi.domain.model.Telefone;
import lombok.Data;

import java.util.List;

@Data
public class EmpresaResponseDTO {

    private Long id;
    private String razaoSocial;
    private Endereco endereco;
    private String cnpj;
    private String nomeFantasia;
    private String inscricaoEstadual;
    private List<Telefone> telefones;
    private String email;
    private String site;
    private String logotipoPath;
}
