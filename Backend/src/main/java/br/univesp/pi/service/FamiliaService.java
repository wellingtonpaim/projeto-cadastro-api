package br.univesp.pi.service;

import br.univesp.pi.domain.dto.FamiliaDTO;
import br.univesp.pi.domain.dto.response.FamiliaResponseDTO;

import java.util.List;

public interface FamiliaService {

    FamiliaResponseDTO salvarFamilia(FamiliaDTO familia);
    List<FamiliaResponseDTO> listarFamilias();
    FamiliaResponseDTO buscarFamiliaPorId(Long codigo);
    List<FamiliaResponseDTO> buscarPorNome(String nome);
    FamiliaResponseDTO atualizarFamilia(Long codigo, FamiliaDTO familia);
    void deletarFamilia(Long codigo);
}
