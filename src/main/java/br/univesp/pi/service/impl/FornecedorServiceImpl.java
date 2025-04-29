package br.univesp.pi.service.impl;

import br.univesp.pi.domain.model.Fornecedor;
import br.univesp.pi.repository.FornecedorRepository;
import br.univesp.pi.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorServiceImpl implements FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public Fornecedor salvar(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    public List<Fornecedor> listarTodos() {
        return fornecedorRepository.findAll();
    }

    public Fornecedor buscarPorCpfOuCnpj(String cpfOuCnpj) {
        return fornecedorRepository.findById(cpfOuCnpj).orElse(null);
    }

    @Override
    public List<Fornecedor> buscarPorNomeOuRazaoSocial(String nomeOuRazaoSocial) {
        return fornecedorRepository.findByNomeOuRazaoSocialContainingIgnoreCase(nomeOuRazaoSocial);
    }

    @Override
    public List<Fornecedor> buscarPorEmail(String email) {
        return fornecedorRepository.findByEmailContainingIgnoreCase(email);
    }

    public Fornecedor atualizar(String cpfOuCnpj, Fornecedor fornecedorAtualizado) {
        Fornecedor fornecedorExistente = buscarPorCpfOuCnpj(cpfOuCnpj);
        if (fornecedorExistente != null) {
            fornecedorAtualizado.setCpfOuCnpj(cpfOuCnpj);
            return fornecedorRepository.save(fornecedorAtualizado);
        }
        return null;
    }

    public void deletar(String cpfOuCnpj) {
        fornecedorRepository.deleteById(cpfOuCnpj);
    }
}

