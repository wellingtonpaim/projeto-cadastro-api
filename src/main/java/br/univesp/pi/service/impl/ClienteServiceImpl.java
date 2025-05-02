package br.univesp.pi.service.impl;

import br.univesp.pi.domain.dto.ClienteCreateDTO;
import br.univesp.pi.domain.dto.ClienteUpdateDTO;
import br.univesp.pi.domain.dto.response.ClienteResponseDTO;
import br.univesp.pi.domain.model.Cliente;
import br.univesp.pi.exception.ApiIllegalArgumentException;
import br.univesp.pi.repository.ClienteRepository;
import br.univesp.pi.service.ClienteService;
import br.univesp.pi.util.MapperUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MapperUtil mapperUtil;

    @Transactional
    @Override
    public ClienteResponseDTO salvarCliente(ClienteCreateDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setCpfOuCnpj(dto.getCpfOuCnpj());
        cliente.setTipoPessoa(dto.getTipoPessoa());
        cliente.setNomeOuRazaoSocial(dto.getNomeOuRazaoSocial());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefones(dto.getTelefones());
        cliente.setEndereco(dto.getEndereco());

        cliente = clienteRepository.save(cliente);

        return mapperUtil.map(cliente, ClienteResponseDTO.class);
    }

    @Override
    public List<ClienteResponseDTO> listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return mapperUtil.mapList(clientes, ClienteResponseDTO.class);
    }

    @Override
    public ClienteResponseDTO buscarClientePorCpfOuCnpj(String cpfOuCnpj) {
        Cliente cliente = clienteRepository.findById(cpfOuCnpj)
                .orElseThrow(() -> new ApiIllegalArgumentException(
                        "Cliente não encontrado com CPF/CNPJ: " + cpfOuCnpj,
                        "Cliente",
                        "cpfOuCnpj",
                        cpfOuCnpj
                ));
        return mapperUtil.map(cliente, ClienteResponseDTO.class);
    }

    @Override
    public List<ClienteResponseDTO> buscarClientePorNomeOuRazaoSocial(String nomeOuRazaoSocial) {
        List<Cliente> clientes = clienteRepository.findByNomeOuRazaoSocialContainingIgnoreCase(nomeOuRazaoSocial);
        if (clientes.isEmpty()) {
            throw new ApiIllegalArgumentException(
                    "Nenhum cliente encontrado com o nome ou razão social: " + nomeOuRazaoSocial,
                    "Cliente",
                    "nomeOuRazaoSocial",
                    nomeOuRazaoSocial
            );
        }
        return mapperUtil.mapList(clientes, ClienteResponseDTO.class);
    }

    @Override
    public List<ClienteResponseDTO> buscarClientePorEmail(String email) {
        List<Cliente> clientes = clienteRepository.findByEmailContainingIgnoreCase(email);
        if (clientes.isEmpty()) {
            throw new ApiIllegalArgumentException(
                    "Nenhum cliente encontrado com o email: " + email,
                    "Cliente",
                    "email",
                    email
            );
        }
        return mapperUtil.mapList(clientes, ClienteResponseDTO.class);
    }

    @Transactional
    @Override
    public ClienteResponseDTO atualizarCliente(String cpfOuCnpj, ClienteUpdateDTO dto) {
        Cliente clienteExistente = clienteRepository.findByCpfOuCnpj(cpfOuCnpj).orElse(null);
        if (clienteExistente == null) {
            throw new ApiIllegalArgumentException(
                    "Cliente não encontrado com CPF/CNPJ: " + cpfOuCnpj,
                    "Cliente",
                    "cpfOuCnpj",
                    cpfOuCnpj
            );
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

        Cliente saved = clienteRepository.save(clienteExistente);

        return mapperUtil.map(saved, ClienteResponseDTO.class);
    }

    @Transactional
    @Override
    public void deletarCliente(String cpfOuCnpj) {
        clienteRepository.deleteById(cpfOuCnpj);
    }
}
