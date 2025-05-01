package br.univesp.pi.service.impl;

import br.univesp.pi.domain.dto.ClienteCreateDTO;
import br.univesp.pi.domain.dto.ClienteUpdateDTO;
import br.univesp.pi.domain.model.Cliente;
import br.univesp.pi.repository.ClienteRepository;
import br.univesp.pi.service.ClienteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    @Override
    public Cliente salvarCliente(ClienteCreateDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setCpfOuCnpj(dto.getCpfOuCnpj());
        cliente.setTipoPessoa(dto.getTipoPessoa());
        cliente.setNomeOuRazaoSocial(dto.getNomeOuRazaoSocial());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefones(dto.getTelefones());
        cliente.setEndereco(dto.getEndereco());
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarClientePorCpfOuCnpj(String cpfOuCnpj) {
        return clienteRepository.findById(cpfOuCnpj).orElse(null);
    }

    @Override
    public List<Cliente> buscarClientePorNomeOuRazaoSocial(String nomeOuRazaoSocial) {
        return clienteRepository.findByNomeOuRazaoSocialContainingIgnoreCase(nomeOuRazaoSocial);
    }

    @Override
    public List<Cliente> buscarClientePorEmail(String email) {
        return clienteRepository.findByEmailContainingIgnoreCase(email);
    }

    @Transactional
    @Override
    public Cliente atualizarCliente(String cpfOuCnpj, ClienteUpdateDTO dto) {
        Cliente clienteExistente = buscarClientePorCpfOuCnpj(cpfOuCnpj);
        if (clienteExistente == null) {
            throw new IllegalArgumentException("Cliente não encontrado com CPF/CNPJ: " + cpfOuCnpj);
        }

        if (dto.getTipoPessoa() != null) {
            clienteExistente.setTipoPessoa(dto.getTipoPessoa());
        }

        if (dto.getNomeOuRazaoSocial() != null) {
            clienteExistente.setNomeOuRazaoSocial(dto.getNomeOuRazaoSocial());
        }

        if (dto.getEmail() != null) {
            clienteExistente.setEmail(dto.getEmail());
        }

        if (dto.getTelefones() != null) {
            clienteExistente.setTelefones(dto.getTelefones());
        }

        if (dto.getEndereco() != null) {
            clienteExistente.setEndereco(dto.getEndereco());
        }

        return clienteRepository.save(clienteExistente);
    }

    @Transactional
    @Override
    public void deletarCliente(String cpfOuCnpj) {
        clienteRepository.deleteById(cpfOuCnpj);
    }
}
