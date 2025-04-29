package br.univesp.pi.service;

import br.univesp.pi.domain.model.Cliente;

import java.util.List;

public interface ClienteService {

    Cliente salvar(Cliente cliente);
    List<Cliente> listarTodos();
    Cliente buscarPorCpfOuCnpj(String cpfOuCnpj);
    List<Cliente> buscarPorNomeOuRazaoSocial(String nomeOuRazaoSocial);
    List<Cliente> buscarPorEmail(String email);
    Cliente atualizar(String cpfOuCnpj, Cliente clienteAtualizado);
    void deletar(String cpfOuCnpj);
}

