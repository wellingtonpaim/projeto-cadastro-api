package br.univesp.pi.controller;

import br.univesp.pi.domain.dto.FornecedorCreateDTO;
import br.univesp.pi.domain.dto.FornecedorUpdateDTO;
import br.univesp.pi.domain.dto.response.ApiResponse;
import br.univesp.pi.domain.dto.response.FornecedorResponseDTO;
import br.univesp.pi.service.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<ApiResponse<FornecedorResponseDTO>> salvarFornecedor(@Valid @RequestBody FornecedorCreateDTO fornecedorDTO) {
        FornecedorResponseDTO fornecedor = fornecedorService.salvarFornecedor(fornecedorDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fornecedor salvo com sucesso", fornecedor));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FornecedorResponseDTO>>> listarFornecedores() {
        List<FornecedorResponseDTO> fornecedores = fornecedorService.listarFornecedores();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fornecedores listados com sucesso", fornecedores));
    }

    @GetMapping("/{cpfOuCnpj}")
    public ResponseEntity<ApiResponse<FornecedorResponseDTO>> buscarFornecedorPorCpfOuCnpj(@PathVariable String cpfOuCnpj) {
        FornecedorResponseDTO fornecedor = fornecedorService.buscarFornecedorPorCpfOuCnpj(cpfOuCnpj);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fornecedor encontrado", fornecedor));
    }

    @GetMapping("/nome/{nomeOuRazaoSocial}")
    public ResponseEntity<ApiResponse<List<FornecedorResponseDTO>>> buscarFornecedorPorNome(@PathVariable String nomeOuRazaoSocial) {
        List<FornecedorResponseDTO> fornecedores = fornecedorService.buscarFornecedorPorNomeOuRazaoSocial(nomeOuRazaoSocial);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fornecedores encontrados por nome", fornecedores));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<List<FornecedorResponseDTO>>> buscarFornecedorPorEmail(@PathVariable String email) {
        List<FornecedorResponseDTO> fornecedores = fornecedorService.buscarFornecedorPorEmail(email);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fornecedores encontrados por e-mail", fornecedores));
    }

    @PutMapping("/{cpfOuCnpj}")
    public ResponseEntity<ApiResponse<FornecedorResponseDTO>> atualizarFornecedor(
            @PathVariable String cpfOuCnpj,
            @Valid @RequestBody FornecedorUpdateDTO fornecedorDTO) {
        FornecedorResponseDTO fornecedorAtualizado = fornecedorService.atualizarFornecedor(cpfOuCnpj, fornecedorDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fornecedor atualizado com sucesso", fornecedorAtualizado));
    }

    @DeleteMapping("/{cpfOuCnpj}")
    public ResponseEntity<ApiResponse<Void>> deletarFornecedor(@PathVariable String cpfOuCnpj) {
        fornecedorService.deletarFornecedor(cpfOuCnpj);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fornecedor deletado com sucesso", null));
    }
}

