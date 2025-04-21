package br.univesp.pi.security.service;

import br.univesp.pi.security.enums.SECURITY_CONSTANTS;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

@Service
public class JWTTokenService {

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .sign(Algorithm.HMAC512(SECURITY_CONSTANTS.SECRET.getBytes()));
    }
}