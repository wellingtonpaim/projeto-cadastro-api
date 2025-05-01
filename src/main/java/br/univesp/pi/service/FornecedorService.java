package br.univesp.pi.service;

import br.univesp.pi.domain.dto.FornecedorCreateDTO;
import br.univesp.pi.domain.dto.FornecedorUpdateDTO;
import br.univesp.pi.domain.model.Fornecedor;

import java.util.List;

public interface FornecedorService {

    Fornecedor salvarFornecedor(FornecedorCreateDTO fornecedor);
    List<Fornecedor> listarFornecedores();
    Fornecedor buscarFornecedorPorCpfOuCnpj(String cpfOuCnpj);
    List<Fornecedor> buscarFornecedorPorNomeOuRazaoSocial(String nomeOuRazaoSocial);
    List<Fornecedor> buscarFornecedorPorEmail(String email);
    Fornecedor atualizarFornecedor(String cpfOuCnpj, FornecedorUpdateDTO fornecedorAtualizado);
    void deletarFornecedor(String cpfOuCnpj);
}

