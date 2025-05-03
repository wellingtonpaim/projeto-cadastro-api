package br.univesp.pi.service.impl;

import br.univesp.pi.domain.dto.FornecedorCreateDTO;
import br.univesp.pi.domain.dto.FornecedorUpdateDTO;
import br.univesp.pi.domain.dto.response.FornecedorResponseDTO;
import br.univesp.pi.domain.model.Fornecedor;
import br.univesp.pi.exception.ApiIllegalArgumentException;
import br.univesp.pi.repository.FornecedorRepository;
import br.univesp.pi.service.FornecedorService;
import br.univesp.pi.util.MapperUtil;
import org.springframework.http.HttpStatus;
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
                        cpfOuCnpj,
                        HttpStatus.NOT_FOUND
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
                    nomeOuRazaoSocial,
                    HttpStatus.NOT_FOUND
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
                    email,
                    HttpStatus.NOT_FOUND
            );
        }
        return mapperUtil.mapList(fornecedores, FornecedorResponseDTO.class);
    }

    @Transactional
    @Override
    public FornecedorResponseDTO atualizarFornecedor(String cpfOuCnpj, FornecedorUpdateDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findByCpfOuCnpj(cpfOuCnpj)
                .orElseThrow(() -> new ApiIllegalArgumentException(
                    "Fornecedor não encontrado com CPF/CNPJ: " + cpfOuCnpj,
                    "Fornecedor",
                    "cpfOuCnpj",
                    cpfOuCnpj,
                    HttpStatus.NOT_FOUND
            ));

        if (dto.getTipoPessoa() != null) {
            fornecedor.setTipoPessoa(dto.getTipoPessoa());
        }

        if (dto.getNomeOuRazaoSocial() != null) {
            fornecedor.setNomeOuRazaoSocial(dto.getNomeOuRazaoSocial());
        }

        if (dto.getEmail() != null) {
            fornecedor.setEmail(dto.getEmail());
        }

        if (dto.getTelefones() != null) {
            fornecedor.setTelefones(dto.getTelefones());
        }

        if (dto.getEndereco() != null) {
            fornecedor.setEndereco(dto.getEndereco());
        }

        Fornecedor saved = fornecedorRepository.save(fornecedor);
        return mapperUtil.map(saved, FornecedorResponseDTO.class);
    }

    @Transactional
    @Override
    public void deletarFornecedor(String cpfOuCnpj) {
        if (!fornecedorRepository.existsById(cpfOuCnpj)) {
            throw new ApiIllegalArgumentException(
                    "Não foi possível deletar. Fornecedor não encontrado com CPF/CNPJ: " + cpfOuCnpj,
                    "Fornecedor",
                    "cpfOuCnpj",
                    cpfOuCnpj,
                    HttpStatus.NOT_FOUND
            );
        }
        fornecedorRepository.deleteById(cpfOuCnpj);
    }
}

