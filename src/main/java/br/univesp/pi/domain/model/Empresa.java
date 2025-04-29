package br.univesp.pi.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

@Data
@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "razao_social")
    private String razaoSocial;

    @Embedded
    private Endereco endereco;

    @CNPJ
    private String cnpj;

    @NotBlank
    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual;

    @ElementCollection
    private List<Telefone> telefone;

    @Column(unique = true)
    @Email
    private String email;

    @NotBlank
    private String site;

    @NotBlank
    @Column(name = "logotipo_path")
    private String logotipoPath;
}

