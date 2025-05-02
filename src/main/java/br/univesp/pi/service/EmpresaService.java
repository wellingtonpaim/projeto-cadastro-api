package br.univesp.pi.service;

import br.univesp.pi.domain.dto.EmpresaCreateDTO;
import br.univesp.pi.domain.dto.EmpresaUpdateDTO;
import br.univesp.pi.domain.dto.response.EmpresaResponseDTO;

import java.util.List;

public interface EmpresaService {

    EmpresaResponseDTO salvarEmpresa(EmpresaCreateDTO empresa);
    List<EmpresaResponseDTO> listarEmpresas();
    EmpresaResponseDTO buscarEmpresasPorId(Long id);
    EmpresaResponseDTO atualizarEmpresa(Long id, EmpresaUpdateDTO empresa);
    void excluirEmpresa(Long id);
}

