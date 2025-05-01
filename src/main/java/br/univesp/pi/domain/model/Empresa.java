package br.univesp.pi.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "razao_social")
    private String razaoSocial;

    @Embedded
    private Endereco endereco;

    private String cnpj;

    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual;

    @ElementCollection
    private List<Telefone> telefone;

    @Column(unique = true)
    private String email;

    private String site;

    @Column(name = "logotipo_path")
    private String logotipoPath;
}

