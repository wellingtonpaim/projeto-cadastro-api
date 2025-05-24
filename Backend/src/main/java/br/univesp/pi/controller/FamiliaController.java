package br.univesp.pi.controller;

import br.univesp.pi.domain.dto.FamiliaDTO;
import br.univesp.pi.domain.dto.response.ApiResponse;
import br.univesp.pi.domain.dto.response.FamiliaResponseDTO;
import br.univesp.pi.service.FamiliaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/familia")
public class FamiliaController {

    @Autowired
    private FamiliaService familiaService;

    @PostMapping
    public ResponseEntity<ApiResponse<FamiliaResponseDTO>> salvarFamilia(@Valid @RequestBody FamiliaDTO familia) {
        FamiliaResponseDTO familiaSalva = familiaService.salvarFamilia(familia);
        return ResponseEntity.ok(new ApiResponse<>(true, "Família salva com sucesso", familiaSalva));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FamiliaResponseDTO>>> listarFamilias() {
        List<FamiliaResponseDTO> familias = familiaService.listarFamilias();
        return ResponseEntity.ok(new ApiResponse<>(true, "Famílias listadas com sucesso", familias));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ApiResponse<FamiliaResponseDTO>> buscarFamiliaPorId(@PathVariable Long codigo) {
        FamiliaResponseDTO familia = familiaService.buscarFamiliaPorId(codigo);
        return ResponseEntity.ok(new ApiResponse<>(true, "Família encontrada", familia));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<ApiResponse<List<FamiliaResponseDTO>>> buscarPorNome(@PathVariable String nome) {
        List<FamiliaResponseDTO> familias = familiaService.buscarPorNome(nome);
        return ResponseEntity.ok(new ApiResponse<>(true, "Famílias encontradas pelo nome", familias));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<ApiResponse<FamiliaResponseDTO>> atualizarFamilia(
            @PathVariable Long codigo,
            @Valid @RequestBody FamiliaDTO familia) {
        FamiliaResponseDTO familiaAtualizada = familiaService.atualizarFamilia(codigo, familia);
        return ResponseEntity.ok(new ApiResponse<>(true, "Família atualizada com sucesso", familiaAtualizada));
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<ApiResponse<Void>> deletarFamilia(@PathVariable Long codigo) {
        familiaService.deletarFamilia(codigo);
        return ResponseEntity.ok(new ApiResponse<>(true, "Família deletada com sucesso", null));
    }
}
