package br.univesp.pi.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "razao_social")
    private String razaoSocial;

    private String cnpj;

    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    @Embedded
    private Endereco endereco;


    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual = "Isento";

    @ElementCollection
    private List<Telefone> telefones;

    @Column(unique = true)
    private String email;

    private String site;

    @Column(name = "logotipo_path")
    private String logotipoPath;

    public String getTelefonesFormatados() {
        if (telefones == null || telefones.isEmpty()) return "";
        return telefones.stream()
                .map(t -> String.format("%d - %d", t.getDdd(), t.getNumero()))
                .collect(Collectors.joining(" ; "));
    }
}

