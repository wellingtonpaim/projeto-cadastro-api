package br.univesp.pi.controller;

import br.univesp.pi.domain.dto.FornecedorCreateDTO;
import br.univesp.pi.domain.dto.FornecedorUpdateDTO;
import br.univesp.pi.domain.dto.response.FornecedorResponseDTO;
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
    public ResponseEntity<FornecedorResponseDTO> salvarFornecedor(@Valid @RequestBody FornecedorCreateDTO fornecedorDTO) {
        return ResponseEntity.ok(fornecedorService.salvarFornecedor(fornecedorDTO));
    }

    @GetMapping
    public ResponseEntity<List<FornecedorResponseDTO>> listarFornecedores() {
        return ResponseEntity.ok(fornecedorService.listarFornecedores());
    }

    @GetMapping("/{cpfOuCnpj}")
    public ResponseEntity<FornecedorResponseDTO> buscarFornecedorPorCpfOuCnpj(@PathVariable String cpfOuCnpj) {
        return ResponseEntity.ok(fornecedorService.buscarFornecedorPorCpfOuCnpj(cpfOuCnpj));
    }

    @GetMapping("/nome/{nomeOuRazaoSocial}")
    public ResponseEntity<List<FornecedorResponseDTO>> buscarFornecedorPorNome(@PathVariable String nomeOuRazaoSocial) {
        List<FornecedorResponseDTO> fornecedores = fornecedorService.buscarFornecedorPorNomeOuRazaoSocial(nomeOuRazaoSocial);
        return ResponseEntity.ok(fornecedores);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<FornecedorResponseDTO>> buscarFornecedorPorEmail(@PathVariable String email) {
        List<FornecedorResponseDTO> fornecedores = fornecedorService.buscarFornecedorPorEmail(email);
        return ResponseEntity.ok(fornecedores);
    }

    @PutMapping("/{cpfOuCnpj}")
    public ResponseEntity<FornecedorResponseDTO> atualizarFornecedor(@PathVariable String cpfOuCnpj, @Valid @RequestBody FornecedorUpdateDTO fornecedorDTO) {
        return ResponseEntity.ok(fornecedorService.atualizarFornecedor(cpfOuCnpj, fornecedorDTO));
    }

    @DeleteMapping("/{cpfOuCnpj}")
    public ResponseEntity<Void> deletarFornecedor(@PathVariable String cpfOuCnpj) {
        fornecedorService.deletarFornecedor(cpfOuCnpj);
        return ResponseEntity.noContent().build();
    }
}

