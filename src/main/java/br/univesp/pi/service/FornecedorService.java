package br.univesp.pi.service;

import br.univesp.pi.domain.model.Fornecedor;

import java.util.List;

public interface FornecedorService {

    Fornecedor salvar(Fornecedor fornecedor);
    List<Fornecedor> listarTodos();
    Fornecedor buscarPorCpfOuCnpj(String cpfOuCnpj);
    List<Fornecedor> buscarPorNomeOuRazaoSocial(String nomeOuRazaoSocial);
    List<Fornecedor> buscarPorEmail(String email);
    Fornecedor atualizar(String cpfOuCnpj, Fornecedor fornecedorAtualizado);
    void deletar(String cpfOuCnpj);
}

