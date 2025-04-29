package br.univesp.pi.security.service;

import br.univesp.pi.domain.model.Usuario;
import br.univesp.pi.repository.UsuarioRepository;
import br.univesp.pi.security.enums.SECURITY_CONSTANTS;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JWTTokenService {

    private final UsuarioRepository usuarioRepository;

    public String generateToken(String username) {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return JWT.create()
                .withSubject(username)
                .withClaim("roles", usuario.getCategoria().name()) // Inclui a role (ADMINISTRADOR/USUARIO)
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000)) // 24h de validade
                .sign(Algorithm.HMAC512(SECURITY_CONSTANTS.SECRET.getBytes()));
    }
}