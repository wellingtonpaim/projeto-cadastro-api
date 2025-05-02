package br.univesp.pi.controller;

import br.univesp.pi.domain.dto.FamiliaDTO;
import br.univesp.pi.domain.dto.response.FamiliaResponseDTO;
import br.univesp.pi.domain.model.Familia;
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
    public ResponseEntity<FamiliaResponseDTO> salvarFamilia(@Valid @RequestBody FamiliaDTO familia) {
        FamiliaResponseDTO familiaSalva = familiaService.salvarFamilia(familia);
        return ResponseEntity.ok(familiaSalva);
    }

    @GetMapping
    public ResponseEntity<List<FamiliaResponseDTO>> listarFamilias() {
        List<FamiliaResponseDTO> familias = familiaService.listarFamilias();
        return ResponseEntity.ok(familias);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<FamiliaResponseDTO> buscarFamiliaPorId(@PathVariable Long codigo) {
        FamiliaResponseDTO familia = familiaService.buscarFamiliaPorId(codigo);
        return ResponseEntity.ok(familia);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<FamiliaResponseDTO>> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(familiaService.buscarPorNome(nome));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<FamiliaResponseDTO> atualizarFamilia(@PathVariable Long codigo, @Valid @RequestBody FamiliaDTO familia) {
        FamiliaResponseDTO familiaAtualizada = familiaService.atualizarFamilia(codigo, familia);
        return ResponseEntity.ok(familiaAtualizada);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletarFamilia(@PathVariable Long codigo) {
        familiaService.deletarFamilia(codigo);
        return ResponseEntity.noContent().build();
    }
}
