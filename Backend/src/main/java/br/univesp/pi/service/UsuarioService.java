package br.univesp.pi.service;

import br.univesp.pi.domain.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioService {

    Usuario salvarUsuario(Usuario usuario);

    Usuario findByEmail(@NotBlank(message = "E-mail é obrigatório") @Email(message = "E-mail inválido") String email);

    void deletarUsuario(Long id);
}
