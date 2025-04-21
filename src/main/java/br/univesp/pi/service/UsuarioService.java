package br.univesp.pi.service;

import br.univesp.pi.domain.model.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsuarioService extends UserDetailsService {

    Usuario salvarUsuario(Usuario usuario);
    List<Usuario> listarUsuarios();
    Usuario buscarUsuarioPorId(String cpfOuCnpj);
    void deletarUsuario(String cpfOuCnpj);
    Usuario atualizarUsuario(String cpfOuCnpj, Usuario usuario);
    UserDetails loadUserByUsername(String username);
}
