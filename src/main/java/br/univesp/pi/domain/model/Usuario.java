package br.univesp.pi.domain.model;

import br.univesp.pi.enumeration.CategoriaUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "nome_usuario")
    private String nomeUsuario;

    @NotBlank
    @Column(unique = true)
    @Email
    private String email;

    @NotBlank
    private String senha;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CategoriaUsuario categoria;

    @Column(columnDefinition = "boolean default false")
    private boolean ativo;

}
