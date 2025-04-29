package br.univesp.pi.service;

import br.univesp.pi.domain.model.Empresa;

import java.util.List;

public interface EmpresaService {

    Empresa salvar(Empresa empresa);
    Empresa atualizar(Long id, Empresa empresa);
    void excluir(Long id);
    Empresa buscarPorId(Long id);
    List<Empresa> listarTodas();
}

