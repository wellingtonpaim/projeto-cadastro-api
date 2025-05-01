package br.univesp.pi.domain.dto;

import br.univesp.pi.domain.model.Endereco;
import br.univesp.pi.domain.model.Telefone;
import br.univesp.pi.enumeration.TipoPessoa;
import br.univesp.pi.validation.CpfCnpj;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.List;

@Data
public class FornecedorUpdateDTO {

    @CpfCnpj
    private String cpfOuCnpj;

    private TipoPessoa tipoPessoa;
    private String nomeOuRazaoSocial;

    @Email
    private String email;

    private List<Telefone> telefones;
    private Endereco endereco;
}
