package br.univesp.pi.service;

import br.univesp.pi.domain.dto.EmpresaCreateDTO;
import br.univesp.pi.domain.dto.EmpresaUpdateDTO;
import br.univesp.pi.domain.model.Empresa;

import java.util.List;

public interface EmpresaService {

    Empresa salvarEmpresa(EmpresaCreateDTO empresa);
    List<Empresa> listarEmpresas();
    Empresa buscarEmpresasPorId(Long id);
    Empresa atualizarEmpresa(Long id, EmpresaUpdateDTO empresa);
    void excluirEmpresa(Long id);
}

