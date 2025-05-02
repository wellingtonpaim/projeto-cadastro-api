package br.univesp.pi.service;

import br.univesp.pi.domain.dto.ClienteCreateDTO;
import br.univesp.pi.domain.dto.ClienteUpdateDTO;
import br.univesp.pi.domain.dto.response.ClienteResponseDTO;

import java.util.List;

public interface ClienteService {

    ClienteResponseDTO salvarCliente(ClienteCreateDTO cliente);
    List<ClienteResponseDTO> listarClientes();
    ClienteResponseDTO buscarClientePorCpfOuCnpj(String cpfOuCnpj);
    List<ClienteResponseDTO> buscarClientePorNomeOuRazaoSocial(String nomeOuRazaoSocial);
    List<ClienteResponseDTO> buscarClientePorEmail(String email);
    ClienteResponseDTO atualizarCliente(String cpfOuCnpj, ClienteUpdateDTO clienteAtualizado);
    void deletarCliente(String cpfOuCnpj);
}

