package br.univesp.pi.controller;

import br.univesp.pi.domain.model.Cliente;
import br.univesp.pi.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.salvar(cliente));
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/{cpfOuCnpj}")
    public ResponseEntity<Cliente> buscarPorCpfOuCnpj(@PathVariable String cpfOuCnpj) {
        return ResponseEntity.ok(clienteService.buscarPorCpfOuCnpj(cpfOuCnpj));
    }

    @GetMapping("/nome/{nomeOuRazaoSocial}")
    public ResponseEntity<List<Cliente>> buscarPorNome(@PathVariable String nomeOuRazaoSocial) {
        List<Cliente> clientes = clienteService.buscarPorNomeOuRazaoSocial(nomeOuRazaoSocial);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<Cliente>> buscarPorEmail(@PathVariable String email) {
        List<Cliente> clientes = clienteService.buscarPorEmail(email);
        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/{cpfOuCnpj}")
    public ResponseEntity<Cliente> atualizar(@PathVariable String cpfOuCnpj, @RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.atualizar(cpfOuCnpj, cliente));
    }

    @DeleteMapping("/{cpfOuCnpj}")
    public ResponseEntity<Void> deletar(@PathVariable String cpfOuCnpj) {
        clienteService.deletar(cpfOuCnpj);
        return ResponseEntity.noContent().build();
    }
}

