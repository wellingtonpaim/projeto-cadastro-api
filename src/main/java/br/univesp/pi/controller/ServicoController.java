package br.univesp.pi.controller;

import br.univesp.pi.domain.dto.ServicoCreateDTO;
import br.univesp.pi.domain.dto.ServicoUpdateDTO;
import br.univesp.pi.domain.model.Servico;
import br.univesp.pi.service.ServicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servico")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @PostMapping
    public ResponseEntity<Servico> salvarServico(@Valid @RequestBody ServicoCreateDTO servico) {
        Servico servicoSalvo = servicoService.salvarServico(servico);
        return ResponseEntity.ok(servicoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Servico>> listarServicos() {
        List<Servico> servicos = servicoService.listarServicos();
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Servico> buscarServicoPorId(@PathVariable Long codigo) {
        Servico servico = servicoService.buscarServicoPorId(codigo);
        return ResponseEntity.ok(servico);
    }

    @GetMapping("/cliente/{cpfOuCnpj}")
    public ResponseEntity<List<Servico>> buscarServicosPorCliente(@PathVariable String cpfOuCnpj) {
        List<Servico> servicos = servicoService.buscarServicosPorClienteId(cpfOuCnpj);
        return ResponseEntity.ok(servicos);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Servico> atualizarServico(@PathVariable Long codigo, @Valid @RequestBody ServicoUpdateDTO servico) {
        Servico servicoAtualizado = servicoService.atualizarServico(codigo, servico);
        return ResponseEntity.ok(servicoAtualizado);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletarServico(@PathVariable Long codigo) {
        servicoService.deletarServico(codigo);
        return ResponseEntity.noContent().build();
    }

}
