package br.univesp.pi.controller;

import br.univesp.pi.domain.dto.ClienteCreateDTO;
import br.univesp.pi.domain.dto.ClienteUpdateDTO;
import br.univesp.pi.domain.model.Cliente;
import br.univesp.pi.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> salvarCliente(@Valid @RequestBody ClienteCreateDTO clienteDTO) {
        return ResponseEntity.ok(clienteService.salvarCliente(clienteDTO));
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/{cpfOuCnpj}")
    public ResponseEntity<Cliente> buscarClientePorCpfOuCnpj(@PathVariable String cpfOuCnpj) {
        return ResponseEntity.ok(clienteService.buscarClientePorCpfOuCnpj(cpfOuCnpj));
    }

    @GetMapping("/nome/{nomeOuRazaoSocial}")
    public ResponseEntity<List<Cliente>> buscarClientePorNome(@PathVariable String nomeOuRazaoSocial) {
        List<Cliente> clientes = clienteService.buscarClientePorNomeOuRazaoSocial(nomeOuRazaoSocial);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<Cliente>> buscarClientePorEmail(@PathVariable String email) {
        List<Cliente> clientes = clienteService.buscarClientePorEmail(email);
        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/{cpfOuCnpj}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable String cpfOuCnpj, @Valid @RequestBody ClienteUpdateDTO clienteDTO) {
        return ResponseEntity.ok(clienteService.atualizarCliente(cpfOuCnpj, clienteDTO));
    }

    @DeleteMapping("/{cpfOuCnpj}")
    public ResponseEntity<Void> deletarCliente(@PathVariable String cpfOuCnpj) {
        clienteService.deletarCliente(cpfOuCnpj);
        return ResponseEntity.noContent().build();
    }
}

