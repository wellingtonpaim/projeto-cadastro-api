package br.univesp.pi.service;

import br.univesp.pi.domain.dto.ServicoCreateDTO;
import br.univesp.pi.domain.dto.ServicoUpdateDTO;
import br.univesp.pi.domain.dto.response.ServicoResponseDTO;

import java.util.List;

public interface ServicoService {

    ServicoResponseDTO salvarServico(ServicoCreateDTO servico);
    List<ServicoResponseDTO> listarServicos();
    ServicoResponseDTO buscarServicoPorId(Long codigo);
    List<ServicoResponseDTO> buscarServicosPorClienteId(String cpfOuCnpj);
    ServicoResponseDTO atualizarServico(Long codigo, ServicoUpdateDTO servico);
    void deletarServico(Long codigo);
}
