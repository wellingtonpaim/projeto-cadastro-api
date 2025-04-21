package br.univesp.pi.domain.model;

import br.univesp.pi.enumeration.CategoriaUsuario;
import br.univesp.pi.enumeration.TipoPessoa;
import br.univesp.pi.validation.CpfCnpj;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Usuario {

    @Id
    @CpfCnpj
    private String CPFouCnpj;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoPessoa tipoPessoa;

    @NotBlank
    private String nomeOuRazaoSocial;

    @Column(unique = true)
    @Email
    private String email;

    @ElementCollection
    private List<Telefone> telefones;

    @Embedded
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CategoriaUsuario categoria;
}
