package br.univesp.pi.service.impl;

import br.univesp.pi.domain.model.Familia;
import br.univesp.pi.repository.FamiliaRepository;
import br.univesp.pi.service.FamiliaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamiliaServiceImpl implements FamiliaService {

    @Autowired
    private FamiliaRepository familiaRepository;

    @Override
    public Familia salvarFamilia(Familia familia) {
        return familiaRepository.save(familia);
    }

    @Override
    public List<Familia> listarFamilias() {
        return familiaRepository.findAll();
    }

    @Override
    public Familia buscarFamiliaPorId(Long codigo) {
        return familiaRepository.findById(codigo).orElse(null);
    }

    @Override
    public void deletarFamilia(Long codigo) {
        familiaRepository.deleteById(codigo);
    }

    @Override
    public Familia atualizarFamilia(Long codigo, Familia familia) {
        Familia familiaExistente = familiaRepository.findById(codigo).orElse(null);
        if (familiaExistente != null) {
            familia.setCodigo(codigo);
            return familiaRepository.save(familia);
        }
        return null;
    }
}
