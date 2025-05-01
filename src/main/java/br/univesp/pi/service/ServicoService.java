package br.univesp.pi.service;

import br.univesp.pi.domain.dto.ServicoCreateDTO;
import br.univesp.pi.domain.dto.ServicoUpdateDTO;
import br.univesp.pi.domain.model.Servico;

import java.util.List;

public interface ServicoService {

    Servico salvarServico(ServicoCreateDTO servico);
    List<Servico> listarServicos();
    Servico buscarServicoPorId(Long codigo);
    void deletarServico(Long codigo);
    Servico atualizarServico(Long codigo, ServicoUpdateDTO servico);
    List<Servico> buscarServicosPorClienteId(String cpfOuCnpj);
}
