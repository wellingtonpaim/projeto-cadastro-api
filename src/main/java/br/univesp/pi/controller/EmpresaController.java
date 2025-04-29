package br.univesp.pi.controller;

import br.univesp.pi.domain.model.Empresa;
import br.univesp.pi.service.EmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService empresaService;

    @PostMapping
    public Empresa salvar(@Valid @RequestBody Empresa empresa) {
        return empresaService.salvar(empresa);
    }

    @PutMapping("/{id}")
    public Empresa atualizar(@PathVariable Long id, @Valid @RequestBody Empresa empresa) {
        return empresaService.atualizar(id, empresa);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        empresaService.excluir(id);
    }

    @GetMapping("/{id}")
    public Empresa buscarPorId(@PathVariable Long id) {
        return empresaService.buscarPorId(id);
    }

    @GetMapping
    public List<Empresa> listarTodas() {
        return empresaService.listarTodas();
    }
}

