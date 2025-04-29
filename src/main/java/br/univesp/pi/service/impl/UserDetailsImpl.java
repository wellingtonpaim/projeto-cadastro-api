package br.univesp.pi.service.impl;

import br.univesp.pi.domain.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class UserDetailsImpl implements UserDetails {

    // Metodo útil para acessar a entidade Usuario quando necessário
    private final Usuario usuario;

    public UserDetailsImpl(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converte a CategoriaUsuario para GrantedAuthority (ROLE_ADMINISTRADOR ou ROLE_USUARIO)
        return Collections.singletonList(
                new SimpleGrantedAuthority(usuario.getCategoria().getRole())
        );
    }

    @Override
    public String getPassword() {
        return usuario.getSenha(); // Deve estar criptografada
    }

    @Override
    public String getUsername() {
        return usuario.getEmail(); // Ou usuario.getEmail() conforme seu fluxo de login
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Pode implementar lógica de expiração se necessário
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Pode implementar bloqueio de conta
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Pode implementar expiração de credenciais
    }

    @Override
    public boolean isEnabled() {
        return usuario.isAtivo(); // Controle de conta ativada por email
    }

}