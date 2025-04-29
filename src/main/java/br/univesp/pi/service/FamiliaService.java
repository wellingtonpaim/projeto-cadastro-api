package br.univesp.pi.service;

import br.univesp.pi.domain.model.Familia;

import java.util.List;

public interface FamiliaService {

    Familia salvarFamilia(Familia familia);
    List<Familia> listarFamilias();
    Familia buscarFamiliaPorId(Long codigo);
    void deletarFamilia(Long codigo);
    Familia atualizarFamilia(Long codigo, Familia familia);
    List<Familia> buscarPorNome(String nome);
}
