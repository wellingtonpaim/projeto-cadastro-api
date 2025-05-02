package br.univesp.pi.service.impl;

import br.univesp.pi.domain.dto.FornecedorCreateDTO;
import br.univesp.pi.domain.dto.FornecedorUpdateDTO;
import br.univesp.pi.domain.dto.response.FornecedorResponseDTO;
import br.univesp.pi.domain.model.Fornecedor;
import br.univesp.pi.exception.ApiIllegalArgumentException;
import br.univesp.pi.repository.FornecedorRepository;
import br.univesp.pi.service.FornecedorService;
import br.univesp.pi.util.MapperUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorServiceImpl implements FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private MapperUtil mapperUtil;

    @Transactional
    @Override
    public FornecedorResponseDTO salvarFornecedor(FornecedorCreateDTO dto) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCpfOuCnpj(dto.getCpfOuCnpj());
        fornecedor.setTipoPessoa(dto.getTipoPessoa());
        fornecedor.setNomeOuRazaoSocial(dto.getNomeOuRazaoSocial());
        fornecedor.setEmail(dto.getEmail());
        fornecedor.setTelefones(dto.getTelefones());
        fornecedor.setEndereco(dto.getEndereco());

        Fornecedor saved = fornecedorRepository.save(fornecedor);

        return mapperUtil.map(saved, FornecedorResponseDTO.class);
    }

    @Override
    public List<FornecedorResponseDTO> listarFornecedores() {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        return mapperUtil.mapList(fornecedores, FornecedorResponseDTO.class);
    }

    @Override
    public FornecedorResponseDTO buscarFornecedorPorCpfOuCnpj(String cpfOuCnpj) {
        Fornecedor fornecedor = fornecedorRepository.findById(cpfOuCnpj)
                .orElseThrow(() -> new ApiIllegalArgumentException(
                        "Fornecedor não encontrado com CPF/CNPJ: " + cpfOuCnpj,
                        "Fornecedor",
                        "cpfOuCnpj",
                        cpfOuCnpj
                ));
        return mapperUtil.map(fornecedor, FornecedorResponseDTO.class);
    }

    @Override
    public List<FornecedorResponseDTO> buscarFornecedorPorNomeOuRazaoSocial(String nomeOuRazaoSocial) {
        List<Fornecedor> fornecedores = fornecedorRepository.findByNomeOuRazaoSocialContainingIgnoreCase(nomeOuRazaoSocial);

        if (fornecedores == null || fornecedores.isEmpty()) {
            throw new ApiIllegalArgumentException(
                    "Nenhum fornecedor encontrado com nome ou razão social contendo: " + nomeOuRazaoSocial,
                    "Fornecedor",
                    "nomeOuRazaoSocial",
                    nomeOuRazaoSocial
            );
        }
        return mapperUtil.mapList(fornecedores, FornecedorResponseDTO.class);
    }

    @Override
    public List<FornecedorResponseDTO> buscarFornecedorPorEmail(String email) {
        List<Fornecedor> fornecedores = fornecedorRepository.findByEmailContainingIgnoreCase(email);

        if (fornecedores == null || fornecedores.isEmpty()) {
            throw new ApiIllegalArgumentException(
                    "Nenhum fornecedor encontrado com email contendo: " + email,
                    "Fornecedor",
                    "email",
                    email
            );
        }
        return mapperUtil.mapList(fornecedores, FornecedorResponseDTO.class);
    }

    @Transactional
    @Override
    public FornecedorResponseDTO atualizarFornecedor(String cpfOuCnpj, FornecedorUpdateDTO dto) {
        Fornecedor fornecedorExistente = fornecedorRepository.findByCpfOuCnpj(cpfOuCnpj).orElse(null);
        if (fornecedorExistente == null) {
            throw new ApiIllegalArgumentException(
                    "Fornecedor não encontrado com CPF/CNPJ: " + cpfOuCnpj,
                    "Fornecedor",
                    "cpfOuCnpj",
                    cpfOuCnpj
            );
        }

        if (dto.getTipoPessoa() != null) {
            fornecedorExistente.setTipoPessoa(dto.getTipoPessoa());
        }

        if (dto.getNomeOuRazaoSocial() != null) {
            fornecedorExistente.setNomeOuRazaoSocial(dto.getNomeOuRazaoSocial());
        }

        if (dto.getEmail() != null) {
            fornecedorExistente.setEmail(dto.getEmail());
        }

        if (dto.getTelefones() != null) {
            fornecedorExistente.setTelefones(dto.getTelefones());
        }

        if (dto.getEndereco() != null) {
            fornecedorExistente.setEndereco(dto.getEndereco());
        }

        Fornecedor saved = fornecedorRepository.save(fornecedorExistente);
        return mapperUtil.map(saved, FornecedorResponseDTO.class);
    }

    @Transactional
    @Override
    public void deletarFornecedor(String cpfOuCnpj) {
        fornecedorRepository.deleteById(cpfOuCnpj);
    }
}

