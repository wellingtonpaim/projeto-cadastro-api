package br.univesp.pi.service;

import br.univesp.pi.domain.model.Pessoa;
import br.univesp.pi.domain.model.Servico;

import java.util.List;

public interface ServicoService {

    Servico salvarServico(Servico servico);
    List<Servico> listarServicos();
    Servico buscarServicoPorId(Long codigo);
    void deletarServico(Long codigo);
    Servico atualizarServico(Long codigo, Servico servico);
    List<Servico> buscarServicosPorCliente(Pessoa cliente);
    List<Servico> buscarServicosPorClienteId(String cpfOuCnpj);
}
