package br.univesp.pi.controller;

import br.univesp.pi.domain.dto.EmpresaCreateDTO;
import br.univesp.pi.domain.dto.EmpresaUpdateDTO;
import br.univesp.pi.domain.dto.response.EmpresaResponseDTO;
import br.univesp.pi.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping
    public EmpresaResponseDTO salvarEmpresa(@Valid @RequestBody EmpresaCreateDTO empresa) {
        return empresaService.salvarEmpresa(empresa);
    }

    @GetMapping
    public List<EmpresaResponseDTO> listarEmpresas() {
        return empresaService.listarEmpresas();
    }

    @GetMapping("/{id}")
    public EmpresaResponseDTO buscarEmpresaPorId(@PathVariable Long id) {
        return empresaService.buscarEmpresasPorId(id);
    }

    @PutMapping("/{id}")
    public EmpresaResponseDTO atualizarEmpresa(@PathVariable Long id, @Valid @RequestBody EmpresaUpdateDTO empresa) {
        return empresaService.atualizarEmpresa(id, empresa);
    }

    @DeleteMapping("/{id}")
    public void excluirEmpresa(@PathVariable Long id) {
        empresaService.excluirEmpresa(id);
    }
}

