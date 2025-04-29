package br.univesp.pi.controller;

import br.univesp.pi.domain.model.Pessoa;
import br.univesp.pi.domain.model.Servico;
import br.univesp.pi.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @PostMapping
    public ResponseEntity<Servico> salvarServico(@RequestBody Servico servico) {
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

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletarServico(@PathVariable Long codigo) {
        servicoService.deletarServico(codigo);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Servico> atualizarServico(@PathVariable Long codigo, @RequestBody Servico servico) {
        Servico servicoAtualizado = servicoService.atualizarServico(codigo, servico);
        return ResponseEntity.ok(servicoAtualizado);
    }

    @GetMapping("/cliente/{cpfOuCnpj}")
    public ResponseEntity<List<Servico>> buscarServicosPorCliente(@PathVariable String cpfOuCnpj) {
        List<Servico> servicos = servicoService.buscarServicosPorClienteId(cpfOuCnpj);
        return ResponseEntity.ok(servicos);
    }

    @PostMapping("/por-cliente")
    public ResponseEntity<List<Servico>> buscarServicosPorCliente(@RequestBody Pessoa cliente) {
        List<Servico> servicos = servicoService.buscarServicosPorCliente(cliente);
        return ResponseEntity.ok(servicos);
    }
}
