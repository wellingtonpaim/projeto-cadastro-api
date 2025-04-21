package br.univesp.pi.security;

import br.univesp.pi.security.enums.SECURITY_CONSTANTS;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader(SECURITY_CONSTANTS.HEADER_STRING);

        if (token != null && token.startsWith(SECURITY_CONSTANTS.TOKEN_PREFIX)) {
            try {
                String user = JWT.require(Algorithm.HMAC512(SECURITY_CONSTANTS.SECRET.getBytes()))
                        .build()
                        .verify(token.replace(SECURITY_CONSTANTS.TOKEN_PREFIX, ""))
                        .getSubject();

                if (user != null) {
                    Authentication auth = new UsernamePasswordAuthenticationToken(
                            user, null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (JWTVerificationException e) {
                logger.debug("Token inválido: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}