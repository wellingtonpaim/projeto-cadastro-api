package br.univesp.pi.service;

import br.univesp.pi.domain.dto.FamiliaDTO;
import br.univesp.pi.domain.model.Familia;

import java.util.List;

public interface FamiliaService {

    Familia salvarFamilia(FamiliaDTO familia);
    List<Familia> listarFamilias();
    Familia buscarFamiliaPorId(Long codigo);
    void deletarFamilia(Long codigo);
    Familia atualizarFamilia(Long codigo, FamiliaDTO familia);
    List<Familia> buscarPorNome(String nome);
}
