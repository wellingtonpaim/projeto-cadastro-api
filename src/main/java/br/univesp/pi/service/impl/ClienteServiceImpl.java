package br.univesp.pi.service.impl;

import br.univesp.pi.domain.dto.ClienteCreateDTO;
import br.univesp.pi.domain.dto.ClienteUpdateDTO;
import br.univesp.pi.domain.dto.response.ClienteResponseDTO;
import br.univesp.pi.domain.model.Cliente;
import br.univesp.pi.exception.ApiIllegalArgumentException;
import br.univesp.pi.repository.ClienteRepository;
import br.univesp.pi.service.ClienteService;
import br.univesp.pi.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        Cliente saved = clienteRepository.save(cliente);
        return mapperUtil.map(saved, ClienteResponseDTO.class);
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
                        cpfOuCnpj,
                        HttpStatus.NOT_FOUND
                ));
        return mapperUtil.map(cliente, ClienteResponseDTO.class);
    }

    @Override
    public List<ClienteResponseDTO> buscarClientePorNomeOuRazaoSocial(String nomeOuRazaoSocial) {
        List<Cliente> clientes = clienteRepository.findByNomeOuRazaoSocialContainingIgnoreCase(nomeOuRazaoSocial);
        if (clientes == null || clientes.isEmpty()) {
            throw new ApiIllegalArgumentException(
                    "Nenhum cliente encontrado com o nome ou razão social: " + nomeOuRazaoSocial,
                    "Cliente",
                    "nomeOuRazaoSocial",
                    nomeOuRazaoSocial,
                    HttpStatus.NOT_FOUND
            );
        }
        return mapperUtil.mapList(clientes, ClienteResponseDTO.class);
    }

    @Override
    public List<ClienteResponseDTO> buscarClientePorEmail(String email) {
        List<Cliente> clientes = clienteRepository.findByEmailContainingIgnoreCase(email);
        if (clientes == null || clientes.isEmpty()) {
            throw new ApiIllegalArgumentException(
                    "Nenhum cliente encontrado com o email: " + email,
                    "Cliente",
                    "email",
                    email,
                    HttpStatus.NOT_FOUND
            );
        }
        return mapperUtil.mapList(clientes, ClienteResponseDTO.class);
    }

    @Transactional
    @Override
    public ClienteResponseDTO atualizarCliente(String cpfOuCnpj, ClienteUpdateDTO dto) {
        Cliente cliente = clienteRepository.findByCpfOuCnpj(cpfOuCnpj)
                .orElseThrow(() -> new ApiIllegalArgumentException(
                    "Cliente não encontrado com CPF/CNPJ: " + cpfOuCnpj,
                    "Cliente",
                    "cpfOuCnpj",
                    cpfOuCnpj,
                    HttpStatus.NOT_FOUND
            ));

        if (dto.getTipoPessoa() != null) { cliente.setTipoPessoa(dto.getTipoPessoa()); }
        if (dto.getNomeOuRazaoSocial() != null) { cliente.setNomeOuRazaoSocial(dto.getNomeOuRazaoSocial()); }
        if (dto.getEmail() != null) { cliente.setEmail(dto.getEmail()); }
        if (dto.getTelefones() != null) { cliente.setTelefones(dto.getTelefones()); }
        if (dto.getEndereco() != null) { cliente.setEndereco(dto.getEndereco()); }

        Cliente saved = clienteRepository.save(cliente);
        return mapperUtil.map(saved, ClienteResponseDTO.class);
    }

    @Transactional
    @Override
    public void deletarCliente(String cpfOuCnpj) {
        if (!clienteRepository.existsById(cpfOuCnpj)) {
            throw new ApiIllegalArgumentException(
                    "Não foi possível deletar. Cliente não encontrado com CPF/CNPJ: " + cpfOuCnpj,
                    "Cliente",
                    "cpfOuCnpj",
                    cpfOuCnpj,
                    HttpStatus.NOT_FOUND
            );
        }
        clienteRepository.deleteById(cpfOuCnpj);
    }
}
