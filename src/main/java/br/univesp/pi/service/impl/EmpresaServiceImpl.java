package br.univesp.pi.service.impl;

import br.univesp.pi.domain.model.Empresa;
import br.univesp.pi.repository.EmpresaRepository;
import br.univesp.pi.service.EmpresaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Transactional
    @Override
    public Empresa salvar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Transactional
    @Override
    public Empresa atualizar(Long id, Empresa empresa) {
        Empresa empresaExistente = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        empresa.setId(id);
        return empresaRepository.save(empresa);
    }

    @Transactional
    @Override
    public void excluir(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new RuntimeException("Empresa não encontrada");
        }
        empresaRepository.deleteById(id);
    }

    @Override
    public Empresa buscarPorId(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
    }

    @Override
    public List<Empresa> listarTodas() {
        return empresaRepository.findAll();
    }
}

