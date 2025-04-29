package br.univesp.pi.controller;

import br.univesp.pi.domain.model.Fornecedor;
import br.univesp.pi.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<Fornecedor> salvar(@RequestBody Fornecedor fornecedor) {
        return ResponseEntity.ok(fornecedorService.salvar(fornecedor));
    }

    @GetMapping
    public ResponseEntity<List<Fornecedor>> listarTodos() {
        return ResponseEntity.ok(fornecedorService.listarTodos());
    }

    @GetMapping("/{cpfOuCnpj}")
    public ResponseEntity<Fornecedor> buscarPorCpfOuCnpj(@PathVariable String cpfOuCnpj) {
        return ResponseEntity.ok(fornecedorService.buscarPorCpfOuCnpj(cpfOuCnpj));
    }

    @GetMapping("/nome/{nomeOuRazaoSocial}")
    public ResponseEntity<List<Fornecedor>> buscarPorNome(@PathVariable String nomeOuRazaoSocial) {
        List<Fornecedor> fornecedores = fornecedorService.buscarPorNomeOuRazaoSocial(nomeOuRazaoSocial);
        return ResponseEntity.ok(fornecedores);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<Fornecedor>> buscarPorEmail(@PathVariable String email) {
        List<Fornecedor> fornecedores = fornecedorService.buscarPorEmail(email);
        return ResponseEntity.ok(fornecedores);
    }

    @PutMapping("/{cpfOuCnpj}")
    public ResponseEntity<Fornecedor> atualizar(@PathVariable String cpfOuCnpj, @RequestBody Fornecedor fornecedor) {
        return ResponseEntity.ok(fornecedorService.atualizar(cpfOuCnpj, fornecedor));
    }

    @DeleteMapping("/{cpfOuCnpj}")
    public ResponseEntity<Void> deletar(@PathVariable String cpfOuCnpj) {
        fornecedorService.deletar(cpfOuCnpj);
        return ResponseEntity.noContent().build();
    }
}

