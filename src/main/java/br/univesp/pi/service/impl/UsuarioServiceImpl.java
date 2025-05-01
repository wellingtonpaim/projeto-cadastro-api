package br.univesp.pi.service.impl;

import br.univesp.pi.domain.model.Usuario;
import br.univesp.pi.repository.UsuarioRepository;
import br.univesp.pi.security.util.PasswordUtils;
import br.univesp.pi.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        usuario.setSenha(PasswordUtils.encodeIfNeeded(usuario.getSenha(), passwordEncoder));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElse(null);
    }

    @Transactional
    @Override
    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
