package br.univesp.pi.service;

import br.univesp.pi.domain.model.Empresa;
import br.univesp.pi.domain.model.Servico;
import jakarta.servlet.http.HttpServletResponse;

public interface ServicoPdfService {

    void gerarPdf(Servico servico, Empresa empresa, HttpServletResponse response) throws Exception;

}

