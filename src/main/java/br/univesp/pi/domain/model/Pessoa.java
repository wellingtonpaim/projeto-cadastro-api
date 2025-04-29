package br.univesp.pi.domain.model;

import br.univesp.pi.enumeration.TipoPessoa;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@MappedSuperclass
public abstract class Pessoa {

    @Id
    @Column(name = "cpf_ou_cnpj")
    private String cpfOuCnpj;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "tipo_pessoa")
    private TipoPessoa tipoPessoa;

    @NotBlank
    @Column(name = "nome_ou_razao_social")
    private String nomeOuRazaoSocial;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @ElementCollection
    private List<Telefone> telefones;

    @Embedded
    private Endereco endereco;
}
