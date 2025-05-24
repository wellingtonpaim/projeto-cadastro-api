package br.univesp.pi.service;

import br.univesp.pi.domain.dto.EmailRequestDTO;

public interface EmailSenderService {
    void sendEmail(EmailRequestDTO emailRequest);
}

