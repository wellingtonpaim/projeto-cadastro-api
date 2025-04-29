package br.univesp.pi.service.impl;

import br.univesp.pi.domain.dto.EmailRequestDTO;
import br.univesp.pi.service.EmailSenderService;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("sendGridEmailSenderService")
public class SendGridEmailSenderServiceImpl implements EmailSenderService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Override
    public void sendEmail(EmailRequestDTO emailRequest) {
        Email from = new Email(emailRequest.getFrom());
        Email to = new Email(emailRequest.getTo());
        Content content = new Content("text/html", emailRequest.getBody());
        Mail mail = new Mail(from, emailRequest.getSubject(), to, content);

        SendGrid sendGrid = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);

            if (response.getStatusCode() >= 400) {
                throw new RuntimeException("Erro ao enviar e-mail: " + response.getBody());
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao tentar enviar e-mail", e);
        }
    }
}

