package br.univesp.pi.service.impl;

import br.univesp.pi.domain.model.Usuario;
import br.univesp.pi.repository.UsuarioRepository;
import br.univesp.pi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarUsuarioPorId(String cpfOuCnpj) {
        return usuarioRepository.findById(cpfOuCnpj).orElse(null);
    }

    @Override
    public void deletarUsuario(String cpfOuCnpj) {
        usuarioRepository.deleteById(cpfOuCnpj);
    }

    @Override
    public Usuario atualizarUsuario(String cpfOuCnpj, Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(cpfOuCnpj).orElse(null);
        if (usuarioExistente != null) {
            usuario.setCPFouCnpj(cpfOuCnpj);
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = buscarUsuarioPorId(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return new UserDetailsImpl(usuario);
    }
}
