package br.univesp.pi.service.impl;

import br.univesp.pi.domain.model.Cliente;
import br.univesp.pi.repository.ClienteRepository;
import br.univesp.pi.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorCpfOuCnpj(String cpfOuCnpj) {
        return clienteRepository.findById(cpfOuCnpj).orElse(null);
    }

    @Override
    public List<Cliente> buscarPorNomeOuRazaoSocial(String nomeOuRazaoSocial) {
        return clienteRepository.findByNomeOuRazaoSocialContainingIgnoreCase(nomeOuRazaoSocial);
    }

    @Override
    public List<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmailContainingIgnoreCase(email);
    }

    public Cliente atualizar(String cpfOuCnpj, Cliente clienteAtualizado) {
        Cliente clienteExistente = buscarPorCpfOuCnpj(cpfOuCnpj);
        if (clienteExistente != null) {
            clienteAtualizado.setCpfOuCnpj(cpfOuCnpj);
            return clienteRepository.save(clienteAtualizado);
        }
        return null;
    }

    public void deletar(String cpfOuCnpj) {
        clienteRepository.deleteById(cpfOuCnpj);
    }
}
