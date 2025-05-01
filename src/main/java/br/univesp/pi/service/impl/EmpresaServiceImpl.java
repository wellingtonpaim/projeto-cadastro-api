package br.univesp.pi.service.impl;

import br.univesp.pi.domain.dto.EmpresaCreateDTO;
import br.univesp.pi.domain.dto.EmpresaUpdateDTO;
import br.univesp.pi.domain.model.Empresa;
import br.univesp.pi.repository.EmpresaRepository;
import br.univesp.pi.service.EmpresaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Transactional
    @Override
    public Empresa salvarEmpresa(EmpresaCreateDTO dto) {
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial(dto.getRazaoSocial());
        empresa.setEndereco(dto.getEndereco());
        empresa.setCnpj(dto.getCnpj());
        empresa.setInscricaoEstadual(dto.getInscricaoEstadual());
        empresa.setTelefone(dto.getTelefones());
        empresa.setEmail(dto.getEmail());
        empresa.setSite(dto.getSite());
        empresa.setLogotipoPath(dto.getLogotipoPath());
        return empresaRepository.save(empresa);
    }

    @Override
    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }

    @Override
    public Empresa buscarEmpresasPorId(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
    }

    @Transactional
    @Override
    public Empresa atualizarEmpresa(Long id, EmpresaUpdateDTO dto) {
        Optional<Empresa> empresaOptional = empresaRepository.findById(id);
        if (empresaOptional.isEmpty()) return null;

        Empresa empresa = empresaOptional.get();

        if (dto.getRazaoSocial() != null) empresa.setRazaoSocial(dto.getRazaoSocial());
        if (dto.getEndereco() != null) empresa.setEndereco(dto.getEndereco());
        if (dto.getCnpj() != null) empresa.setCnpj(dto.getCnpj());
        if (dto.getInscricaoEstadual() != null) empresa.setInscricaoEstadual(dto.getInscricaoEstadual());
        if (dto.getTelefone() != null) empresa.setTelefone(dto.getTelefone());
        if (dto.getEmail() != null) empresa.setEmail(dto.getEmail());
        if (dto.getSite() != null) empresa.setSite(dto.getSite());
        if (dto.getLogotipoPath() != null) empresa.setLogotipoPath(dto.getLogotipoPath());

        return empresaRepository.save(empresa);
    }

    @Transactional
    @Override
    public void excluirEmpresa(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new RuntimeException("Empresa não encontrada");
        }
        empresaRepository.deleteById(id);
    }
}

