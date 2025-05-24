package br.univesp.pi.service.impl;

import br.univesp.pi.domain.model.Usuario;
import br.univesp.pi.exception.ApiIllegalArgumentException;
import br.univesp.pi.repository.UsuarioRepository;
import br.univesp.pi.security.repository.ConfirmationTokenRepository;
import br.univesp.pi.security.util.PasswordUtils;
import br.univesp.pi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        usuario.setSenha(PasswordUtils.encodeIfNeeded(usuario.getSenha(), passwordEncoder));
        return usuarioRepository.saveAndFlush(usuario);
    }

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    @Override
    public void deletarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ApiIllegalArgumentException(
                        "Usuário não encontrado para exclusão",
                        "Usuário",
                        "id",
                        id,
                        HttpStatus.NOT_FOUND
                ));

        confirmationTokenRepository.deleteByUsuario(usuario);

        usuarioRepository.deleteById(id);
    }
}
