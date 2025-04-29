package br.univesp.pi.controller;

import br.univesp.pi.domain.model.Empresa;
import br.univesp.pi.domain.model.Servico;
import br.univesp.pi.repository.EmpresaRepository;
import br.univesp.pi.repository.ServicoRepository;
import br.univesp.pi.service.ServicoPdfService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
public class ServicoPdfController {

    private final ServicoRepository servicoRepository;
    private final EmpresaRepository empresaRepository;
    private final ServicoPdfService servicoPdfService;

    @GetMapping("/{codigo}/pdf")
    public void gerarPdf(@PathVariable Long codigo, HttpServletResponse response) throws Exception {
        Servico servico = servicoRepository.findById(codigo)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        Empresa empresa = empresaRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Empresa não cadastrada"));

        servicoPdfService.gerarPdf(servico, empresa, response);
    }
}

