package br.univesp.pi.service.impl;

import br.univesp.pi.domain.dto.EmailRequestDTO;
import br.univesp.pi.service.EmailSenderService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service("sendPulseEmailSenderService")
@RequiredArgsConstructor
public class SendPulseEmailSenderServiceImpl implements EmailSenderService {

    @Value("${sendpulse.client.id}")
    private String clientId;

    @Value("${sendpulse.client.secret}")
    private String clientSecret;

    @Value("${sendpulse.api.url}")
    private String sendPulseApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void sendEmail(EmailRequestDTO emailRequest) {
        String accessToken = obterAccessToken();

        String url = sendPulseApiUrl + "/smtp/emails/send";

        Map<String, Object> emailPayload = new HashMap<>();
        emailPayload.put("email", new HashMap<>() {{
            put("html", emailRequest.getBody());
            put("subject", emailRequest.getSubject());
            put("from", new HashMap<String, String>() {{
                put("name", "Sistema");
                put("email", emailRequest.getFrom());
            }});
            put("to", new HashMap[]{
                    new HashMap<String, String>() {{
                        put("email", emailRequest.getTo());
                    }}
            });
        }});

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(emailPayload, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Erro ao enviar e-mail via SendPulse: " + response.getBody());
        }
    }

    private String obterAccessToken() {
        String url = sendPulseApiUrl + "/oauth/access_token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> authPayload = new HashMap<>();
        authPayload.put("grant_type", "client_credentials");
        authPayload.put("client_id", clientId);
        authPayload.put("client_secret", clientSecret);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(authPayload, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Erro ao obter access_token do SendPulse: " + response.getBody());
        }

        try {
            JsonNode node = objectMapper.readTree(response.getBody());
            return node.get("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler access_token do SendPulse", e);
        }
    }
}

