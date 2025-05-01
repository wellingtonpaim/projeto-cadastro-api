package br.univesp.pi.service;

import br.univesp.pi.domain.model.Empresa;
import br.univesp.pi.domain.model.Servico;

public interface ServicoPdfService {
    byte[] gerarPdf(Servico servico, Empresa empresa) throws Exception;
}
