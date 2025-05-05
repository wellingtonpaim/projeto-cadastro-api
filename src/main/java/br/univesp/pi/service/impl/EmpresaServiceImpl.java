package br.univesp.pi.service.impl;

import br.univesp.pi.domain.dto.EmpresaCreateDTO;
import br.univesp.pi.domain.dto.EmpresaUpdateDTO;
import br.univesp.pi.domain.dto.response.EmpresaResponseDTO;
import br.univesp.pi.domain.model.Empresa;
import br.univesp.pi.exception.ApiIllegalArgumentException;
import br.univesp.pi.repository.EmpresaRepository;
import br.univesp.pi.service.EmpresaService;
import br.univesp.pi.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private MapperUtil mapperUtil;

    @Transactional
    @Override
    public EmpresaResponseDTO salvarEmpresa(EmpresaCreateDTO dto) {
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial(dto.getRazaoSocial());
        empresa.setEndereco(dto.getEndereco());
        empresa.setCnpj(dto.getCnpj());
        empresa.setNomeFantasia(dto.getNomeFantasia());
        empresa.setInscricaoEstadual(dto.getInscricaoEstadual());
        empresa.setTelefones(dto.getTelefones());
        empresa.setEmail(dto.getEmail());
        empresa.setSite(dto.getSite());
        empresa.setLogotipoPath(dto.getLogotipoPath());

        Empresa saved = empresaRepository.save(empresa);
        return mapperUtil.map(saved, EmpresaResponseDTO.class);
    }

    @Override
    public List<EmpresaResponseDTO> listarEmpresas() {
        List<Empresa> empresas = empresaRepository.findAll();
        return mapperUtil.mapList(empresas, EmpresaResponseDTO.class);
    }

    @Override
    public EmpresaResponseDTO buscarEmpresasPorId(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new ApiIllegalArgumentException(
                        "Empresa não encontrada com ID: " + id,
                        "Empresa",
                        "id",
                        id.toString(),
                        HttpStatus.NOT_FOUND
                ));
        return mapperUtil.map(empresa, EmpresaResponseDTO.class);
    }

    @Transactional
    @Override
    public EmpresaResponseDTO atualizarEmpresa(Long id, EmpresaUpdateDTO dto) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new ApiIllegalArgumentException(
                        "Empresa não encontrada com ID: " + id,
                        "Empresa",
                        "id",
                        id.toString(),
                        HttpStatus.NOT_FOUND
                ));

        if (dto.getRazaoSocial() != null) empresa.setRazaoSocial(dto.getRazaoSocial());
        if (dto.getEndereco() != null) empresa.setEndereco(dto.getEndereco());
        if (dto.getCnpj() != null) empresa.setCnpj(dto.getCnpj());
        if (dto.getNomeFantasia() != null) empresa.setNomeFantasia(dto.getNomeFantasia());
        if (dto.getInscricaoEstadual() != null) empresa.setInscricaoEstadual(dto.getInscricaoEstadual());
        if (dto.getTelefones() != null) empresa.setTelefones(dto.getTelefones());
        if (dto.getEmail() != null) empresa.setEmail(dto.getEmail());
        if (dto.getSite() != null) empresa.setSite(dto.getSite());
        if (dto.getLogotipoPath() != null) empresa.setLogotipoPath(dto.getLogotipoPath());

        Empresa saved = empresaRepository.save(empresa);
        return mapperUtil.map(saved, EmpresaResponseDTO.class);
    }

    @Transactional
    @Override
    public void excluirEmpresa(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new ApiIllegalArgumentException(
                    "Empresa não encontrada com ID: " + id,
                    "Empresa",
                    "id",
                    id.toString(),
                    HttpStatus.NOT_FOUND
            );
        }
        empresaRepository.deleteById(id);
    }
}
