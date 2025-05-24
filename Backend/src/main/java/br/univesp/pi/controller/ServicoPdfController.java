package br.univesp.pi.controller;

import br.univesp.pi.domain.model.Empresa;
import br.univesp.pi.domain.model.Servico;
import br.univesp.pi.repository.EmpresaRepository;
import br.univesp.pi.repository.ServicoRepository;
import br.univesp.pi.service.ServicoPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servico-pdf")
@RequiredArgsConstructor
public class ServicoPdfController {

    private final ServicoRepository servicoRepository;
    private final EmpresaRepository empresaRepository;
    private final ServicoPdfService servicoPdfService;

    @GetMapping("/{codigo}")
    public ResponseEntity<byte[]> gerarPdf(@PathVariable Long codigo) throws Exception {
        Servico servico = servicoRepository.findById(codigo)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        Empresa empresa = empresaRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Empresa não cadastrada"));

        byte[] pdfBytes = servicoPdfService.gerarPdf(servico, empresa);

        String filename = "ordem-servico-" + servico.getCodigo() + ".pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
