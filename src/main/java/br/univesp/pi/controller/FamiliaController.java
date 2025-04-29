package br.univesp.pi.controller;

import br.univesp.pi.domain.model.Familia;
import br.univesp.pi.domain.model.Produto;
import br.univesp.pi.service.FamiliaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/familias")
public class FamiliaController {

    @Autowired
    private FamiliaService familiaService;

    @PostMapping
    public ResponseEntity<Familia> salvarFamilia(@RequestBody Familia familia) {
        Familia familiaSalva = familiaService.salvarFamilia(familia);
        return ResponseEntity.ok(familiaSalva);
    }

    @GetMapping
    public ResponseEntity<List<Familia>> listarFamilias() {
        List<Familia> familias = familiaService.listarFamilias();
        return ResponseEntity.ok(familias);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Familia> buscarFamiliaPorId(@PathVariable Long codigo) {
        Familia familia = familiaService.buscarFamiliaPorId(codigo);
        return ResponseEntity.ok(familia);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletarFamilia(@PathVariable Long codigo) {
        familiaService.deletarFamilia(codigo);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Familia> atualizarFamilia(@PathVariable Long codigo, @RequestBody Familia familia) {
        Familia familiaAtualizada = familiaService.atualizarFamilia(codigo, familia);
        return ResponseEntity.ok(familiaAtualizada);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Familia>> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(familiaService.buscarPorNome(nome));
    }
}
