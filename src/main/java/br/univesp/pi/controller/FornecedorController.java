package br.univesp.pi.controller;

import br.univesp.pi.domain.dto.FornecedorCreateDTO;
import br.univesp.pi.domain.dto.FornecedorUpdateDTO;
import br.univesp.pi.domain.model.Fornecedor;
import br.univesp.pi.service.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedore")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<Fornecedor> salvarFornecedor(@Valid @RequestBody FornecedorCreateDTO fornecedorDTO) {
        return ResponseEntity.ok(fornecedorService.salvarFornecedor(fornecedorDTO));
    }

    @GetMapping
    public ResponseEntity<List<Fornecedor>> listarFornecedores() {
        return ResponseEntity.ok(fornecedorService.listarFornecedores());
    }

    @GetMapping("/{cpfOuCnpj}")
    public ResponseEntity<Fornecedor> buscarFornecedorPorCpfOuCnpj(@PathVariable String cpfOuCnpj) {
        return ResponseEntity.ok(fornecedorService.buscarFornecedorPorCpfOuCnpj(cpfOuCnpj));
    }

    @GetMapping("/nome/{nomeOuRazaoSocial}")
    public ResponseEntity<List<Fornecedor>> buscarFornecedorPorNome(@PathVariable String nomeOuRazaoSocial) {
        List<Fornecedor> fornecedores = fornecedorService.buscarFornecedorPorNomeOuRazaoSocial(nomeOuRazaoSocial);
        return ResponseEntity.ok(fornecedores);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<Fornecedor>> buscarFornecedorPorEmail(@PathVariable String email) {
        List<Fornecedor> fornecedores = fornecedorService.buscarFornecedorPorEmail(email);
        return ResponseEntity.ok(fornecedores);
    }

    @PutMapping("/{cpfOuCnpj}")
    public ResponseEntity<Fornecedor> atualizarFornecedor(@PathVariable String cpfOuCnpj, @Valid @RequestBody FornecedorUpdateDTO fornecedorDTO) {
        return ResponseEntity.ok(fornecedorService.atualizarFornecedor(cpfOuCnpj, fornecedorDTO));
    }

    @DeleteMapping("/{cpfOuCnpj}")
    public ResponseEntity<Void> deletarFornecedor(@PathVariable String cpfOuCnpj) {
        fornecedorService.deletarFornecedor(cpfOuCnpj);
        return ResponseEntity.noContent().build();
    }
}

