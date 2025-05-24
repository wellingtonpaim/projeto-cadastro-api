package br.univesp.pi.service.impl;

import br.univesp.pi.domain.model.Empresa;
import br.univesp.pi.domain.model.Servico;
import br.univesp.pi.factory.CustomReplacedElementFactory;
import br.univesp.pi.service.ServicoPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ServicoPdfServiceImpl implements ServicoPdfService {

    private final TemplateEngine templateEngine;

    @Override
    public byte[] gerarPdf(Servico servico, Empresa empresa) throws Exception {
        // Configurar resolver para evitar problemas de cache
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false); // Desativa cache para desenvolvimento

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("servico", servico);
        context.setVariable("empresa", empresa);
        context.setVariable("dataImpressao", LocalDateTime.now());

        // Converte a imagem da logo para base64
        String logoBase64 = "";
        if (empresa.getLogotipoPath() != null) {
            try {
                ClassPathResource imgFile = new ClassPathResource(empresa.getLogotipoPath());
                byte[] imageBytes = imgFile.getInputStream().readAllBytes();
                logoBase64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
            } catch (Exception e) {
                System.err.println("Erro ao carregar logotipo: " + e.getMessage());
            }
        }
        context.setVariable("logoBase64", logoBase64);

        String htmlContent = engine.process("servico-pdf", context);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();

            // Configuração importante para melhor renderização
            renderer.getSharedContext().setReplacedElementFactory(
                    new CustomReplacedElementFactory(renderer.getSharedContext().getReplacedElementFactory())
            );
            renderer.getSharedContext().setPrint(true);
            renderer.getSharedContext().setInteractive(false);

            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        }
    }
}