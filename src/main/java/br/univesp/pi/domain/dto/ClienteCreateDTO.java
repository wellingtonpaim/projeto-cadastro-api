package br.univesp.pi.domain.dto;

import br.univesp.pi.domain.model.Endereco;
import br.univesp.pi.domain.model.Telefone;
import br.univesp.pi.enumeration.TipoPessoa;
import br.univesp.pi.validation.CpfCnpj;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ClienteCreateDTO {

    @NotBlank
    @CpfCnpj
    private String cpfOuCnpj;

    @NotNull
    private TipoPessoa tipoPessoa;

    @NotNull
    private String nomeOuRazaoSocial;

    @NotNull
    @Email
    private String email;

    private List<Telefone> telefones;
    private Endereco endereco;
}
