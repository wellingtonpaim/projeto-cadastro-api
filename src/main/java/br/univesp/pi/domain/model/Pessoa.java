package br.univesp.pi.domain.model;

import br.univesp.pi.enumeration.TipoPessoa;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@MappedSuperclass
public abstract class Pessoa {

    @Id
    @Column(name = "cpf_ou_cnpj")
    private String cpfOuCnpj;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa")
    private TipoPessoa tipoPessoa;

    @Column(name = "nome_ou_razao_social")
    private String nomeOuRazaoSocial;

    @Column(unique = true)
    private String email;

    @ElementCollection
    private List<Telefone> telefones;

    @Embedded
    private Endereco endereco;
}
