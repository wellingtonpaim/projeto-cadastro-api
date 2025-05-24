package br.univesp.pi.controller;

import br.univesp.pi.domain.dto.EmpresaCreateDTO;
import br.univesp.pi.domain.dto.EmpresaUpdateDTO;
import br.univesp.pi.domain.dto.response.ApiResponse;
import br.univesp.pi.domain.dto.response.EmpresaResponseDTO;
import br.univesp.pi.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<ApiResponse<EmpresaResponseDTO>> salvarEmpresa(@Valid @RequestBody EmpresaCreateDTO empresaDTO) {
        EmpresaResponseDTO empresa = empresaService.salvarEmpresa(empresaDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Empresa criada com sucesso", empresa));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmpresaResponseDTO>>> listarEmpresas() {
        List<EmpresaResponseDTO> empresas = empresaService.listarEmpresas();
        return ResponseEntity.ok(new ApiResponse<>(true, "Empresas listadas com sucesso", empresas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmpresaResponseDTO>> buscarEmpresaPorId(@PathVariable Long id) {
        EmpresaResponseDTO empresa = empresaService.buscarEmpresasPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Empresa encontrada com sucesso", empresa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmpresaResponseDTO>> atualizarEmpresa(
            @PathVariable Long id,
            @Valid @RequestBody EmpresaUpdateDTO empresaDTO) {
        EmpresaResponseDTO atualizada = empresaService.atualizarEmpresa(id, empresaDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Empresa atualizada com sucesso", atualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> excluirEmpresa(@PathVariable Long id) {
        empresaService.excluirEmpresa(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Empresa excluída com sucesso", null));
    }
}
