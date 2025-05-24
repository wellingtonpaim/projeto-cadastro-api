package br.univesp.pi.domain.dto;

import br.univesp.pi.validation.SenhaValida;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioRegisterDTO {

    @NotBlank(message = "Nome de usuário é obrigatório")
    private String nomeUsuario;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @SenhaValida
    private String senha;

    private String categoria = "USUARIO";
}