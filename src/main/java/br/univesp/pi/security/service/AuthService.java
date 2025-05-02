package br.univesp.pi.security.service;

import br.univesp.pi.domain.dto.EmailRequestDTO;
import br.univesp.pi.domain.dto.UsuarioRegisterDTO;
import br.univesp.pi.domain.model.Usuario;
import br.univesp.pi.security.model.ConfirmationToken;
import br.univesp.pi.security.repository.ConfirmationTokenRepository;
import br.univesp.pi.service.EmailSenderService;
import br.univesp.pi.service.UsuarioService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class AuthService {

    private final UsuarioService usuarioService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailSenderService emailSenderService;

    public AuthService(
            UsuarioService usuarioService,
            ConfirmationTokenRepository confirmationTokenRepository,
            Map<String, EmailSenderService> emailSenderServices,
            @Value("${email.sender.impl}") String emailSenderImpl
    ) {
        this.usuarioService = usuarioService;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailSenderService = emailSenderServices.get(emailSenderImpl);
        if (this.emailSenderService == null) {
            throw new IllegalArgumentException("Não foi encontrado um serviço de envio de email com o nome: " + emailSenderImpl);
        }
    }

    @Value("${app.email.sender}")
    private String senderEmail;

    @Transactional
    public void register(UsuarioRegisterDTO dto) {

        if (usuarioService.findByEmail(dto.getEmail()) != null) {
            throw new IllegalArgumentException("Email já cadastrado!");
        }

        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(dto.getNomeUsuario());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setCategoria(dto.getCategoria());
        usuario.setAtivo(false);

        Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, usuarioSalvo);
        confirmationTokenRepository.save(confirmationToken);

        sendConfirmationEmailAsync(usuarioSalvo, token);
    }

    private void sendConfirmationEmailAsync(Usuario usuario, String token) {
        CompletableFuture.runAsync(() -> {
            String confirmationUrl = "http://localhost:8080/auth/confirmar?token=" + token;
            String htmlBody = "<p>Olá " + usuario.getNomeUsuario() + ",</p>"
                    + "<p>Por favor, confirme seu cadastro clicando no link abaixo:</p>"
                    + "<a href=\"" + confirmationUrl + "\">Confirmar Cadastro</a>";

            EmailRequestDTO emailRequest = EmailRequestDTO.builder()
                    .subject("Confirmação de Cadastro")
                    .body(htmlBody)
                    .to(usuario.getEmail())
                    .from(senderEmail)
                    .build();

            emailSenderService.sendEmail(emailRequest);
        });
    }

    public void confirmEmail(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

        if (confirmationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expirado");
        }

        Usuario usuario = confirmationToken.getUsuario();
        usuario.setAtivo(true);
        usuarioService.salvarUsuario(usuario);
        confirmationTokenRepository.delete(confirmationToken);
    }

    public void deleteUserByEmail(String email) {
        Usuario usuario = usuarioService.findByEmail(email);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        usuarioService.deletarUsuario(usuario.getId());
    }
}

