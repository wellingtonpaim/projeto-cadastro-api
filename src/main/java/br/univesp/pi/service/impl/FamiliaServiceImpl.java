package br.univesp.pi.service.impl;

import br.univesp.pi.domain.dto.FamiliaDTO;
import br.univesp.pi.domain.model.Familia;
import br.univesp.pi.repository.FamiliaRepository;
import br.univesp.pi.service.FamiliaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamiliaServiceImpl implements FamiliaService {

    @Autowired
    private FamiliaRepository familiaRepository;

    @Transactional
    @Override
    public Familia salvarFamilia(FamiliaDTO dto) {
        Familia familia = new Familia();
        familia.setNome(dto.getNome());
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
    public List<Familia> buscarPorNome(String nome) {
        return familiaRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Transactional
    @Override
    public Familia atualizarFamilia(Long codigo, FamiliaDTO dto) {
        Familia familiaExistente = familiaRepository.findById(codigo).orElse(null);
        if (familiaExistente != null) {
            familiaExistente.setNome(dto.getNome());
            return familiaRepository.save(familiaExistente);
        }
        return null;
    }

    @Transactional
    @Override
    public void deletarFamilia(Long codigo) {
        familiaRepository.deleteById(codigo);
    }

}
