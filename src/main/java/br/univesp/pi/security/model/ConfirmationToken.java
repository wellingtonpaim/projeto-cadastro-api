package br.univesp.pi.security.model;

import br.univesp.pi.domain.model.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private Usuario usuario;

    private LocalDateTime expiryDate;

    public ConfirmationToken() {
        this.expiryDate = LocalDateTime.now().plusHours(24);
    }

    public ConfirmationToken(String token, Usuario usuario) {
        this();
        this.token = token;
        this.usuario = usuario;
    }
}