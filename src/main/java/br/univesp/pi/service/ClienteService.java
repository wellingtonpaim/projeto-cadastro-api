package br.univesp.pi.service;

import br.univesp.pi.domain.dto.ClienteCreateDTO;
import br.univesp.pi.domain.dto.ClienteUpdateDTO;
import br.univesp.pi.domain.model.Cliente;

import java.util.List;

public interface ClienteService {

    Cliente salvarCliente(ClienteCreateDTO cliente);
    List<Cliente> listarClientes();
    Cliente buscarClientePorCpfOuCnpj(String cpfOuCnpj);
    List<Cliente> buscarClientePorNomeOuRazaoSocial(String nomeOuRazaoSocial);
    List<Cliente> buscarClientePorEmail(String email);
    Cliente atualizarCliente(String cpfOuCnpj, ClienteUpdateDTO clienteAtualizado);
    void deletarCliente(String cpfOuCnpj);
}

