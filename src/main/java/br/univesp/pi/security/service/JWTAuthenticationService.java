package br.univesp.pi.security.service;

import br.univesp.pi.security.enums.SECURITY_CONSTANTS;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;

@Log4j2
public class JWTAuthenticationService {

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SECURITY_CONSTANTS.HEADER_STRING);

        if (token != null && token.startsWith(SECURITY_CONSTANTS.TOKEN_PREFIX)) {
            try {
                String user = JWT.require(Algorithm.HMAC512(SECURITY_CONSTANTS.SECRET.getBytes()))
                        .build()
                        .verify(token.replace(SECURITY_CONSTANTS.TOKEN_PREFIX, ""))
                        .getSubject();

                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                }
            } catch (JWTVerificationException e) {
                log.debug("Erro na validação do token: {}", e.getMessage());
            }
        }
        return null;
    }
}