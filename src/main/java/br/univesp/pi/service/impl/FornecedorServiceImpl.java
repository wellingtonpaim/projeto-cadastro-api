package br.univesp.pi.service.impl;

import br.univesp.pi.domain.dto.FornecedorCreateDTO;
import br.univesp.pi.domain.dto.FornecedorUpdateDTO;
import br.univesp.pi.domain.model.Fornecedor;
import br.univesp.pi.repository.FornecedorRepository;
import br.univesp.pi.service.FornecedorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorServiceImpl implements FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Transactional
    @Override
    public Fornecedor salvarFornecedor(FornecedorCreateDTO dto) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCpfOuCnpj(dto.getCpfOuCnpj());
        fornecedor.setTipoPessoa(dto.getTipoPessoa());
        fornecedor.setNomeOuRazaoSocial(dto.getNomeOuRazaoSocial());
        fornecedor.setEmail(dto.getEmail());
        fornecedor.setTelefones(dto.getTelefones());
        fornecedor.setEndereco(dto.getEndereco());
        return fornecedorRepository.save(fornecedor);
    }

    @Override
    public List<Fornecedor> listarFornecedores() {
        return fornecedorRepository.findAll();
    }

    @Override
    public Fornecedor buscarFornecedorPorCpfOuCnpj(String cpfOuCnpj) {
        return fornecedorRepository.findById(cpfOuCnpj).orElse(null);
    }

    @Override
    public List<Fornecedor> buscarFornecedorPorNomeOuRazaoSocial(String nomeOuRazaoSocial) {
        return fornecedorRepository.findByNomeOuRazaoSocialContainingIgnoreCase(nomeOuRazaoSocial);
    }

    @Override
    public List<Fornecedor> buscarFornecedorPorEmail(String email) {
        return fornecedorRepository.findByEmailContainingIgnoreCase(email);
    }

    @Transactional
    @Override
    public Fornecedor atualizarFornecedor(String cpfOuCnpj, FornecedorUpdateDTO dto) {
        Fornecedor fornecedorExistente = buscarFornecedorPorCpfOuCnpj(cpfOuCnpj);
        if (fornecedorExistente == null) {
            throw new IllegalArgumentException("Fornecedor não encontrado com CPF/CNPJ: " + cpfOuCnpj);
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

        return fornecedorRepository.save(fornecedorExistente);
    }

    @Transactional
    @Override
    public void deletarFornecedor(String cpfOuCnpj) {
        fornecedorRepository.deleteById(cpfOuCnpj);
    }
}

