package br.univesp.pi.service.impl;

import br.univesp.pi.domain.dto.FamiliaDTO;
import br.univesp.pi.domain.dto.response.FamiliaResponseDTO;
import br.univesp.pi.domain.model.Familia;
import br.univesp.pi.exception.ApiIllegalArgumentException;
import br.univesp.pi.repository.FamiliaRepository;
import br.univesp.pi.service.FamiliaService;
import br.univesp.pi.util.MapperUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamiliaServiceImpl implements FamiliaService {

    @Autowired
    private FamiliaRepository familiaRepository;

    @Autowired
    private MapperUtil mapperUtil;

    @Transactional
    @Override
    public FamiliaResponseDTO salvarFamilia(FamiliaDTO dto) {
        Familia familia = new Familia();
        familia.setNome(dto.getNome());

        Familia saved = familiaRepository.save(familia);

        return mapperUtil.map(saved, FamiliaResponseDTO.class);
    }

    @Override
    public List<FamiliaResponseDTO> listarFamilias() {
        List<Familia> familias = familiaRepository.findAll();
        return mapperUtil.mapList(familias, FamiliaResponseDTO.class);
    }

    @Override
    public FamiliaResponseDTO buscarFamiliaPorId(Long codigo) {
        Familia familia = familiaRepository.findById(codigo)
                .orElseThrow(() -> new ApiIllegalArgumentException(
                        "Família não encontrada com ID: " + codigo,
                        "Família",
                        "codigo",
                        codigo.toString()
                ));
        return mapperUtil.map(familia, FamiliaResponseDTO.class);
    }

    @Override
    public List<FamiliaResponseDTO> buscarPorNome(String nome) {
        List<Familia> familias = familiaRepository.findByNomeContainingIgnoreCase(nome);
        if (familias == null || familias.isEmpty()) {
            throw new ApiIllegalArgumentException(
                    "Nenhuma família encontrada com nome contendo: " + nome,
                    "Família",
                    "nome",
                    nome
            );
        }
        return mapperUtil.mapList(familias, FamiliaResponseDTO.class);
    }

    @Transactional
    @Override
    public FamiliaResponseDTO atualizarFamilia(Long codigo, FamiliaDTO dto) {
        Familia familiaExistente = familiaRepository.findById(codigo).orElse(null);
        if (familiaExistente != null) {
            familiaExistente.setNome(dto.getNome());

           Familia saved = familiaRepository.save(familiaExistente);

            return mapperUtil.map(saved, FamiliaResponseDTO.class);
        }
        return null;
    }

    @Transactional
    @Override
    public void deletarFamilia(Long codigo) {
        familiaRepository.deleteById(codigo);
    }

}
