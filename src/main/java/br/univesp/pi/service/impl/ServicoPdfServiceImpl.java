package br.univesp.pi.service.impl;

import br.univesp.pi.domain.model.Empresa;
import br.univesp.pi.domain.model.Servico;
import br.univesp.pi.service.ServicoPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ServicoPdfServiceImpl implements ServicoPdfService {

    private final TemplateEngine templateEngine;

    public byte[] gerarPdf(Servico servico, Empresa empresa) throws Exception {
        Context context = new Context();
        context.setVariable("servico", servico);
        context.setVariable("empresa", empresa);
        context.setVariable("dataImpressao", java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        String htmlContent = templateEngine.process("servico-pdf", context);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        }
    }
}
